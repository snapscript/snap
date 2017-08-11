package org.snapscript.core.bind;

import static org.snapscript.core.convert.Score.INVALID;

import java.lang.reflect.Proxy;
import java.util.List;

import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.convert.ProxyTypeFilter;
import org.snapscript.core.convert.Score;
import org.snapscript.core.function.ArgumentConverter;
import org.snapscript.core.function.EmptyFunction;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Signature;
import org.snapscript.core.stack.ThreadStack;

public class DelegateFunctionMatcher {
   
   private final FunctionCacheIndexer<Type> indexer;
   private final FunctionCacheTable<Type> table;
   private final FunctionKeyBuilder builder;
   private final FunctionPathFinder finder;
   private final ProxyTypeFilter filter;
   private final TypeExtractor extractor;
   private final ThreadStack stack;
   private final Function invalid;
   
   public DelegateFunctionMatcher(TypeExtractor extractor, ThreadStack stack) {
      this.indexer = new TypeCacheIndexer();
      this.table = new FunctionCacheTable<Type>(indexer);
      this.builder = new FunctionKeyBuilder(extractor);
      this.finder = new FunctionPathFinder();
      this.invalid = new EmptyFunction(null);
      this.filter = new ProxyTypeFilter();
      this.extractor = extractor;
      this.stack = stack;
   }
   
   public FunctionPointer match(Proxy value, String name, Object... values) throws Exception { 
      Type type = extractor.getType(value);
      Function function = resolve(type, name, values);
      
      if(function != invalid) {
         return new FunctionPointer(function, stack, values);
      }
      return null;
   }

   public Function resolve(Type type, String name, Object... values) throws Exception { 
      Object key = builder.create(name, values);
      FunctionCache cache = table.get(type);
      Function function = cache.fetch(key); // all type functions
      
      if(function == null) {
         List<Type> path = finder.findPath(type, name); // should only provide non-abstract methods
         Score best = INVALID;
         
         for(Type entry : path) {
            if(filter.accept(entry)) {
               List<Function> functions = entry.getFunctions();
               int size = functions.size();
               
               for(int i = size - 1; i >= 0; i--) {
                  Function next = functions.get(i);
                  String method = next.getName();
                  
                  if(name.equals(method)) {
                     Signature signature = next.getSignature();
                     ArgumentConverter match = signature.getConverter();
                     Score score = match.score(values);
      
                     if(score.compareTo(best) > 0) {
                        function = next;
                        best = score;
                     }
                  }
               }
            }
         }
         if(best.isFinal()) {
            if(function == null) {
               function = invalid;
            }
            cache.cache(key, function);
         }
      }      
      return function;
   }
}