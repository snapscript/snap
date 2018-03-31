package org.snapscript.core.function.find;

import java.util.List;

import org.snapscript.core.Type;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.function.Signature;

public class ScopeFunctionIndex implements FunctionIndex {

   private FunctionKeyBuilder builder;
   private FunctionSearcher matcher;
   private FunctionIndexPartition[] caches;
   private int limit; 
   
   public ScopeFunctionIndex(FunctionSearcher matcher, FunctionKeyBuilder builder) {
      this(matcher, builder, 20);
   }
   
   public ScopeFunctionIndex(FunctionSearcher matcher, FunctionKeyBuilder builder, int limit) {
      this.caches = new FunctionIndexPartition[2];
      this.matcher = matcher;
      this.builder = builder;
      this.limit = limit;
   }
   
   @Override
   public FunctionPointer resolve(String name, Type... arguments) throws Exception {
      int size = arguments.length;
      
      if(size < caches.length) {
         FunctionIndexPartition cache = caches[size];
         
         if(cache != null) {
            return cache.resolve(name, arguments);
         }
      }
      return null;
   }
   
   @Override
   public FunctionPointer resolve(String name, Object... arguments) throws Exception {
      int size = arguments.length;
      
      if(size < caches.length) {
         FunctionIndexPartition cache = caches[size];
         
         if(cache != null) {
            return cache.resolve(name, arguments);
         }
      }
      return null;
   }

   @Override
   public void index(FunctionPointer call) throws Exception {
      Function function = call.getFunction();
      Signature signature = function.getSignature();
      List<Parameter> parameters = signature.getParameters();
      int size = parameters.size();
      
      if(signature.isVariable()) {
         int maximum = size + limit;
         int minimum = size -1; // vargs with no value
         
         for(int i = maximum; i >= minimum; i--) { // limit variable arguments
            index(call, i);
         }
      } else {
         index(call, size);
      }
   }
   
   private void index(FunctionPointer call, int size) throws Exception {
      if(size >= caches.length) {
         FunctionIndexPartition[] copy = new FunctionIndexPartition[size + 1];
         
         for(int i = 0; i < caches.length; i++){
            copy[i] = caches[i];
         }
         caches = copy;
      }
      FunctionIndexPartition cache = caches[size];
      
      if(cache == null) {
         cache = caches[size] = new FunctionIndexPartition(matcher, builder);
      }
      cache.index(call);
   }
}