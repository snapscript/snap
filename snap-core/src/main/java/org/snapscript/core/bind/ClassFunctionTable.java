package org.snapscript.core.bind;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;
import org.snapscript.core.Type;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Signature;

public class ClassFunctionTable implements FunctionTable {
   
   private final Cache<Object, Function> cache;
   private final List<Function> functions;
   private final FunctionKeyBuilder builder;
   private final FunctionSearcher searcher;
   
   public ClassFunctionTable(FunctionSearcher searcher, FunctionKeyBuilder builder) {
      this.cache = new CopyOnWriteCache<Object, Function>();
      this.functions = new ArrayList<Function>();
      this.searcher = searcher;
      this.builder = builder;
   }

   @Override
   public Function resolve(String name, Type... types) throws Exception {
      Object key = builder.create(name, types);
      Function function = cache.fetch(key);
      
      if(function == null) {
         Function match = searcher.search(functions, name, types);
         Signature signature = match.getSignature();
         
         if(signature.isAbsolute()) {
            cache.cache(key, match);
         }
         return validate(match);
      }
      return validate(function);
   }

   @Override
   public Function resolve(String name, Object... list) throws Exception {
      Object key = builder.create(name, list);
      Function function = cache.fetch(key);
      
      if(function == null) {
         Function match = searcher.search(functions, name, list);
         Signature signature = match.getSignature();
         
         if(signature.isAbsolute()) {
            cache.cache(key, match);
         }
         return validate(match);
      }
      return validate(function);
   }

   @Override
   public void update(Function function) throws Exception {
      functions.add(function);
   }

   private Function validate(Function function) {
      Signature signature = function.getSignature();
      
      if(!signature.isInvalid()) {
         return function;
      }
      return null;
   }
}
