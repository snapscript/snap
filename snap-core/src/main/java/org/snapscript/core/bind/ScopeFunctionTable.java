package org.snapscript.core.bind;

import java.util.List;

import org.snapscript.core.Type;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.function.Signature;

public class ScopeFunctionTable implements FunctionTable {

   private FunctionKeyBuilder builder;
   private FunctionSearcher matcher;
   private FunctionCache[] caches;
   private int limit; 
   
   public ScopeFunctionTable(FunctionSearcher matcher, FunctionKeyBuilder builder) {
      this(matcher, builder, 20);
   }
   
   public ScopeFunctionTable(FunctionSearcher matcher, FunctionKeyBuilder builder, int limit) {
      this.caches = new FunctionCache[2];
      this.matcher = matcher;
      this.builder = builder;
      this.limit = limit;
   }
   
   @Override
   public FunctionCall resolve(String name, Type... arguments) throws Exception {
      int size = arguments.length;
      
      if(size < caches.length) {
         FunctionCache cache = caches[size];
         
         if(cache != null) {
            return cache.resolve(name, arguments);
         }
      }
      return null;
   }
   
   @Override
   public FunctionCall resolve(String name, Object... arguments) throws Exception {
      int size = arguments.length;
      
      if(size < caches.length) {
         FunctionCache cache = caches[size];
         
         if(cache != null) {
            return cache.resolve(name, arguments);
         }
      }
      return null;
   }

   @Override
   public void update(FunctionCall call) throws Exception {
      Function function = call.getFunction();
      Signature signature = function.getSignature();
      List<Parameter> parameters = signature.getParameters();
      int size = parameters.size();
      
      if(signature.isVariable()) {
         int maximum = size + limit;
         int minimum = size -1; // vargs with no value
         
         for(int i = maximum; i >= minimum; i--) { // limit variable arguments
            update(call, i);
         }
      } else {
         update(call, size);
      }
   }
   
   private void update(FunctionCall call, int size) throws Exception {
      if(size >= caches.length) {
         FunctionCache[] copy = new FunctionCache[size + 1];
         
         for(int i = 0; i < caches.length; i++){
            copy[i] = caches[i];
         }
         caches = copy;
      }
      FunctionCache cache = caches[size];
      
      if(cache == null) {
         cache = caches[size] = new FunctionCache(matcher, builder);
      }
      cache.update(call);
   }
}
