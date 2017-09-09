package org.snapscript.core.bind;

import java.util.List;

import org.snapscript.core.Type;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.function.Signature;

public class FunctionTable {

   private FunctionKeyBuilder builder;
   private FunctionSearcher matcher;
   private FunctionCache[] caches;
   private int filter; 
   private int limit; 
   
   public FunctionTable(FunctionSearcher matcher, FunctionKeyBuilder builder, int filter) {
      this(matcher, builder, filter, 200);
   }
   
   public FunctionTable(FunctionSearcher matcher, FunctionKeyBuilder builder, int filter, int limit) {
      this.caches = new FunctionCache[0];
      this.matcher = matcher;
      this.builder = builder;
      this.filter = filter;
      this.limit = limit;
   }
   
   public Function resolve(String name, Type... arguments) throws Exception {
      int size = arguments.length;
      
      if(size < caches.length) {
         FunctionCache cache = caches[size];
         
         if(cache != null) {
            return cache.resolve(name, arguments);
         }
      }
      return null;
   }
   
   public Function resolve(String name, Object... arguments) throws Exception {
      int size = arguments.length;
      
      if(size < caches.length) {
         FunctionCache cache = caches[size];
         
         if(cache != null) {
            return cache.resolve(name, arguments);
         }
      }
      return null;
   }

   public void update(Function function) throws Exception {
      Signature signature = function.getSignature();
      List<Parameter> parameters = signature.getParameters();
      int size = parameters.size();
      
      if(signature.isVariable()) {
         int maximum = size + limit;
         int minimum = size -1; // vargs with no value
         
         for(int i = maximum; i >= minimum; i--) { // limit variable arguments
            update(function, i);
         }
      } else {
         update(function, size);
      }
   }
   
   private void update(Function function, int size) throws Exception {
      if(size >= caches.length) {
         FunctionCache[] copy = new FunctionCache[size + 1];
         
         for(int i = 0; i < caches.length; i++){
            copy[i] = caches[i];
         }
         caches = copy;
      }
      FunctionCache cache = caches[size];
      
      if(cache == null) {
         cache = caches[size] = new FunctionCache(matcher, builder, filter);
      }
      cache.update(function);
   }
}
