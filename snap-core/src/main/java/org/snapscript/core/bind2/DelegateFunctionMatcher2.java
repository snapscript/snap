package org.snapscript.core.bind2;

import static org.snapscript.core.convert.Score.INVALID;

import java.util.List;

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;
import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.bind.FunctionCacheIndexer;
import org.snapscript.core.bind.FunctionKeyBuilder;
import org.snapscript.core.bind.FunctionPathFinder;
import org.snapscript.core.bind.FunctionPointer;
import org.snapscript.core.bind.TypeCacheIndexer;
import org.snapscript.core.convert.Delegate;
import org.snapscript.core.convert.ProxyTypeFilter;
import org.snapscript.core.convert.Score;
import org.snapscript.core.function.ArgumentConverter;
import org.snapscript.core.function.EmptyFunction;
import org.snapscript.core.function.EmptySignature;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Signature;
import org.snapscript.core.stack.ThreadStack;

public class DelegateFunctionMatcher2 {
   
   private final FunctionCacheIndexer<Type> indexer;
   private final Cache<Type, FunctionGroupTable> table;
   private final FunctionKeyBuilder builder;
   private final FunctionPathFinder finder;
   private final ProxyTypeFilter filter;
   private final FunctionMatcher matcher;
   private final TypeExtractor extractor;
   private final ThreadStack stack;
   private final Signature signature;
   private final Function invalid;
   
   public DelegateFunctionMatcher2(TypeExtractor extractor, ThreadStack stack) {
      this.indexer = new TypeCacheIndexer();
      this.table = new CopyOnWriteCache<Type, FunctionGroupTable>();
      this.builder = new FunctionKeyBuilder(extractor);
      this.finder = new FunctionPathFinder();
      this.matcher = new FunctionMatcherImpl();
      this.signature = new EmptySignature();
      this.invalid = new EmptyFunction(signature);
      this.filter = new ProxyTypeFilter();
      this.extractor = extractor;
      this.stack = stack;
   }
   
   public FunctionPointer match(Delegate value, String name, Object... values) throws Exception { 
      Type type = extractor.getType(value);
      Function function = resolve(type, name, values);
      
      if(function != null) {
         return new FunctionPointer(function, stack, values);
      }
      return null;
   }
/*
 *       FunctionGroupTable cache = table.fetch(type);
      
      if(type.toString().contains("default.B")) {
         System.err.println();
      }
      if(cache == null) {
         FunctionGroupTable group = new FunctionGroupTable(matcher, builder);
         List<Type> path = finder.findPath(type, name); // should only provide non-abstract methods
         int size = path.size();

         for(int i = size - 1; i >= 0; i--) {
            Type entry = path.get(i);
            List<Function> functions = entry.getFunctions();

            for(Function function : functions){
               group.update(function);
            }
         }
         table.cache(type, group);
         return group.resolve(name, values);
      }
      return cache.resolve(name, values);
 */
   public Function resolve(Type type, String name, Object... values) throws Exception { 
      FunctionGroupTable cache = table.fetch(type);
      
      if(cache == null) {
         FunctionGroupTable group = new FunctionGroupTable(matcher, builder, 0);
         List<Type> path = finder.findPath(type, name); // should only provide non-abstract methods
         int size = path.size();

         for(int i = size - 1; i >= 0; i--) {
            Type entry = path.get(i);
            
            if(filter.accept(entry)) {
               List<Function> functions = entry.getFunctions();
   
               for(Function function : functions){
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
               Signature signature = next.getSignature();
               ArgumentConverter match = signature.getConverter();
               Score score = match.score(types);

               if(score.compareTo(best) > 0) {
                  function = next;
                  best = score;
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
               Signature signature = next.getSignature();
               ArgumentConverter match = signature.getConverter();
               Score score = match.score(values);

               if(score.compareTo(best) > 0) {
                  function = next;
                  best = score;
               }
            }
            return function;
         }      
         return invalid;
      }
   }
}