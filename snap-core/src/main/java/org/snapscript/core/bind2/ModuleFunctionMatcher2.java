package org.snapscript.core.bind2;

import static org.snapscript.core.convert.Score.INVALID;

import java.util.List;

import org.snapscript.core.ModifierType;
import org.snapscript.core.Module;
import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.bind.FunctionCacheIndexer;
import org.snapscript.core.bind.FunctionKeyBuilder;
import org.snapscript.core.bind.FunctionPointer;
import org.snapscript.core.bind.ModuleCacheIndexer;
import org.snapscript.core.convert.Score;
import org.snapscript.core.function.ArgumentConverter;
import org.snapscript.core.function.EmptyFunction;
import org.snapscript.core.function.EmptySignature;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Signature;
import org.snapscript.core.stack.ThreadStack;

public class ModuleFunctionMatcher2 {
   
   private final FunctionCacheIndexer<Module> indexer;
   private final FunctionGroupTable[] table = new FunctionGroupTable[100];
   private final FunctionKeyBuilder builder;
   private final FunctionMatcher matcher;
   private final ThreadStack stack;
   private final Signature signature;
   private final Function invalid;
   
   public ModuleFunctionMatcher2(TypeExtractor extractor, ThreadStack stack) {
      this.indexer = new ModuleCacheIndexer();
      this.builder = new FunctionKeyBuilder(extractor);
      this.signature = new EmptySignature();
      this.invalid = new EmptyFunction(signature);
      this.matcher = new FunctionMatcherImpl();
      this.stack = stack;
   }
   
   public FunctionPointer match(Module module, String name, Object... values) throws Exception { 
      Function function = resolve(module, name, values);
      
      if(function != null) {
         return new FunctionPointer(function, stack, values);
      }
      return null;
   }

   public Function resolve(Module module, String name, Object... values) throws Exception { 
      int index = module.getOrder();
      FunctionGroupTable cache = table[index];
      
      if(cache == null) {
         FunctionGroupTable group = new FunctionGroupTable(matcher, builder, 0);
         List<Function> functions = module.getFunctions();

         for(Function function : functions){
            group.update(function);
         }
         table[index] = group;
         return group.resolve(name, values);
      }
      return cache.resolve(name, values);
   }
   
   public class FunctionMatcherImpl implements FunctionMatcher {
   
      @Override
      public Function match(List<Function> functions, Type... types) throws Exception { 
         int size = functions.size();
         
         if(size > 0) {
            Function function = invalid;
            Score best = INVALID;
            
            for(int i = size - 1; i >= 0; i--) {
               Function next = functions.get(i);
               int modifiers = next.getModifiers();
               
               if(!ModifierType.isAbstract(modifiers)) {
                  Signature signature = next.getSignature();
                  ArgumentConverter match = signature.getConverter();
                  Score score = match.score(types);
   
                  if(score.compareTo(best) > 0) {
                     function = next;
                     best = score;
                  }
               }
            }
            return function;
         }
         return invalid;
      }
   
      @Override
      public Function match(List<Function> functions, Object... values) throws Exception { 
         int size = functions.size();
         
         if(size > 0) {
            Function function = invalid;
            Score best = INVALID;
               
            for(int i = size - 1; i >= 0; i--) {
               Function next = functions.get(i);
               int modifiers = next.getModifiers();
               
               if(!ModifierType.isAbstract(modifiers)) {
                  Signature signature = next.getSignature();
                  ArgumentConverter match = signature.getConverter();
                  Score score = match.score(values);
   
                  if(score.compareTo(best) > 0) {
                     function = next;
                     best = score;
                  }
               }
            }
            return function;
         }      
         return invalid;
      }
   }
}