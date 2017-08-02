package org.snapscript.core.bind;

import static org.snapscript.core.convert.Score.INVALID;

import java.util.List;

import org.snapscript.core.Module;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.convert.Score;
import org.snapscript.core.function.ArgumentConverter;
import org.snapscript.core.function.EmptyFunction;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Signature;
import org.snapscript.core.stack.ThreadStack;

public class ModuleFunctionMatcher {
   
   private final FunctionCacheIndexer<Module> indexer;
   private final FunctionCacheTable<Module> table;
   private final FunctionKeyBuilder builder;
   private final ThreadStack stack;
   private final Function invalid;
   
   public ModuleFunctionMatcher(TypeExtractor extractor, ThreadStack stack) {
      this.indexer = new ModuleCacheIndexer();
      this.table = new FunctionCacheTable<Module>(indexer);
      this.builder = new FunctionKeyBuilder(extractor);
      this.invalid = new EmptyFunction(null);
      this.stack = stack;
   }

   public FunctionPointer match(Module module, String name, Object... values) throws Exception {
      Object key = builder.create(name, values);
      FunctionCache cache = table.get(module);
      Function function = cache.fetch(key); // static and module functions
      
      if(function == null) {
         List<Function> functions = module.getFunctions();
         int size = functions.size();
         Score best = INVALID;
   
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