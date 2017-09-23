package org.snapscript.core.bind;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;
import org.snapscript.core.Type;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Signature;

public class ClassFunctionTable implements FunctionTable {
   
   private final Cache<Object, FunctionCall> cache;
   private final List<FunctionCall> calls;
   private final FunctionKeyBuilder builder;
   private final FunctionSearcher searcher;
   
   public ClassFunctionTable(FunctionSearcher searcher, FunctionKeyBuilder builder) {
      this.cache = new CopyOnWriteCache<Object, FunctionCall>();
      this.calls = new ArrayList<FunctionCall>();
      this.searcher = searcher;
      this.builder = builder;
   }

   @Override
   public FunctionCall resolve(String name, Type... types) throws Exception {
      Object key = builder.create(name, types);
      FunctionCall call = cache.fetch(key);
      
      if(call == null) {
         FunctionCall match = searcher.search(calls, name, types);
         Function function = match.getFunction();
         Signature signature = function.getSignature();
         
         if(signature.isAbsolute()) {
            cache.cache(key, match);
         }
         return validate(match);
      }
      return validate(call);
   }

   @Override
   public FunctionCall resolve(String name, Object... list) throws Exception {
      Object key = builder.create(name, list);
      FunctionCall call = cache.fetch(key);
      
      if(call == null) {
         FunctionCall match = searcher.search(calls, name, list);
         Function function = match.getFunction();
         Signature signature = function.getSignature();
         
         if(signature.isAbsolute()) {
            cache.cache(key, match);
         }
         return validate(match);
      }
      return validate(call);
   }

   private FunctionCall validate(FunctionCall call) {
      Function function = call.getFunction();
      Signature signature = function.getSignature();
      
      if(!signature.isInvalid()) {
         return call;
      }
      return null;
   }
   
   @Override
   public void update(FunctionCall call) throws Exception {
      calls.add(call);
   }
}