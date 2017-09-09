package org.snapscript.core.bind2;

import static org.snapscript.core.ModifierType.ABSTRACT;
import static org.snapscript.core.convert.Score.INVALID;

import java.util.List;

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;
import org.snapscript.core.ModifierType;
import org.snapscript.core.Reserved;
import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.bind.FunctionKeyBuilder;
import org.snapscript.core.bind.FunctionPathFinder;
import org.snapscript.core.convert.Score;
import org.snapscript.core.function.ArgumentConverter;
import org.snapscript.core.function.EmptyFunction;
import org.snapscript.core.function.EmptySignature;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Signature;

public class FunctionResolver2 {
   
   private final Cache<Type, FunctionGroupTable> table;
   private final FunctionKeyBuilder builder;
   private final FunctionPathFinder finder;
   private final FunctionMatcher matcher;
   private final Signature signature;
   private final Function invalid;
   
   public FunctionResolver2(TypeExtractor extractor) {
      this.table = new CopyOnWriteCache<Type, FunctionGroupTable>();
      this.builder = new FunctionKeyBuilder(extractor);
      this.finder = new FunctionPathFinder();
      this.signature = new EmptySignature();
      this.invalid = new EmptyFunction(signature);
      this.matcher = new FunctionMatcherImpl();
   }
   
   public Function resolve(Type type, String name, Type... types) throws Exception { 
      FunctionGroupTable cache = table.fetch(type);

      if(cache == null) {
         FunctionGroupTable group = new FunctionGroupTable(matcher, builder, ABSTRACT.mask);
         List<Type> path = finder.findPath(type, name); // should only provide non-abstract methods
         int size = path.size();

         for(int i = size - 1; i >= 0; i--) {
            Type entry = path.get(i);
            List<Function> functions = entry.getFunctions();

            for(Function function : functions){
               String key = function.getName();
               Type parent = function.getType();
               
               if(!key.equals(Reserved.TYPE_CONSTRUCTOR) || parent == type) {
                  group.update(function);
               }
            }
         }
         table.cache(type, group);
         return group.resolve(name, types);
      }
      return cache.resolve(name, types);
   }

   public Function resolve(Type type, String name, Object... values) throws Exception { 
      FunctionGroupTable cache = table.fetch(type);

      if(cache == null) {
         FunctionGroupTable group = new FunctionGroupTable(matcher, builder, ABSTRACT.mask);
         List<Type> path = finder.findPath(type, name); // should only provide non-abstract methods
         int size = path.size();

         for(int i = size - 1; i >= 0; i--) {
            Type entry = path.get(i);
            List<Function> functions = entry.getFunctions();

            for(Function function : functions){
               String key = function.getName();
               Type parent = function.getType();
               
               if(!key.equals(Reserved.TYPE_CONSTRUCTOR) || parent == type) {
                  group.update(function);
               }
            }
         }
         table.cache(type, group);
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
