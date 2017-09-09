package org.snapscript.core.bind2;

import java.util.List;

import org.snapscript.core.Type;
import org.snapscript.core.bind.FunctionKeyBuilder;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.function.Signature;

public class FunctionGroupTable {

   private FunctionGroupCache[] caches;
   private FunctionKeyBuilder builder;
   private FunctionMatcher matcher;
   private int filter; // filter modifiers
   private int limit; // limit for varargs
   
   public FunctionGroupTable(FunctionMatcher matcher, FunctionKeyBuilder builder, int filter) {
      this(matcher, builder, filter, 200);
   }
   
   public FunctionGroupTable(FunctionMatcher matcher, FunctionKeyBuilder builder, int filter, int limit) {
      this.caches = new FunctionGroupCache[0];
      this.matcher = matcher;
      this.builder = builder;
      this.filter = filter;
      this.limit = limit;
   }
   
   public Function resolve(String name, Type... arguments) throws Exception {
      int size = arguments.length;
      
      if(size < caches.length) {
         FunctionGroupCache cache = caches[size];
         
         if(cache != null) {
            return cache.resolve(name, arguments);
         }
      }
      return null;
   }
   
   public Function resolve(String name, Object... arguments) throws Exception {
      int size = arguments.length;
      
      if(size < caches.length) {
         FunctionGroupCache cache = caches[size];
         
         if(cache != null) {
            return cache.resolve(name, arguments);
         }
      }
      return null;
   }

   public void update(Function function) {
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
   
   private void update(Function function, int size) {
      if(size >= caches.length) {
         FunctionGroupCache[] copy = new FunctionGroupCache[size + 1];
         
         for(int i = 0; i < caches.length; i++){
            copy[i] = caches[i];
         }
         caches = copy;
      }
      FunctionGroupCache cache = caches[size];
      
      if(cache == null) {
         cache = caches[size] = new FunctionGroupCache(matcher, builder, filter);
      }
      cache.update(function);
   }
}
