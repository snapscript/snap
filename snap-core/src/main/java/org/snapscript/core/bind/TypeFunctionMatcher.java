package org.snapscript.core.bind;

import java.util.List;

import org.snapscript.core.ModifierType;
import org.snapscript.core.Type;
import org.snapscript.core.TypeLoader;
import org.snapscript.core.convert.Score;
import org.snapscript.core.error.ThreadStack;
import org.snapscript.core.function.ArgumentConverter;
import org.snapscript.core.function.EmptyFunction;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Signature;

public class TypeFunctionMatcher {
   
   private final FunctionCacheIndexer<Type> indexer;
   private final FunctionCacheTable<Type> table;
   private final FunctionKeyBuilder builder;
   private final FunctionPathFinder finder;
   private final ThreadStack stack;
   private final Function invalid;
   
   public TypeFunctionMatcher(TypeLoader loader, ThreadStack stack) {
      this.indexer = new TypeCacheIndexer();
      this.table = new FunctionCacheTable<Type>(indexer);
      this.builder = new FunctionKeyBuilder(loader);
      this.finder = new FunctionPathFinder();
      this.invalid = new EmptyFunction(null);
      this.stack = stack;
   }
   
   public FunctionPointer match(Type type, String name, Object... values) throws Exception { 
      Object key = builder.create(type, name, values); 
      FunctionCache cache = table.get(type);
      Function function = cache.fetch(key); // static and module functions
      
      if(function == null) {
         List<Type> path = finder.findPath(type, name); // should only provide non-abstract methods
         Score best = Score.INVALID;
         
         for(Type entry : path) {
            List<Function> functions = entry.getFunctions();
            int size = functions.size();
            
            for(int i = size - 1; i >= 0; i--) {
               Function next = functions.get(i);
               int modifiers = next.getModifiers();
               
               if(ModifierType.isStatic(modifiers)) { // must be static
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
      if(function != invalid) {
         return new FunctionPointer(function, stack, values);
      }
      return null;
   }
}
