package org.snapscript.core.function.index;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Origin;
import org.snapscript.core.function.Signature;
import org.snapscript.core.type.Type;

public class ClassFunctionIndex implements FunctionIndex {
   
   private final Cache<Object, FunctionPointer> cache;
   private final List<FunctionPointer> pointers;
   private final FunctionKeyBuilder builder;
   private final FunctionReducer reducer;
   
   public ClassFunctionIndex(FunctionReducer reducer, FunctionKeyBuilder builder) {
      this.cache = new CopyOnWriteCache<Object, FunctionPointer>();
      this.pointers = new ArrayList<FunctionPointer>();
      this.reducer = reducer;
      this.builder = builder;
   }

   @Override
   public List<FunctionPointer> resolve(int modifiers) {
      List<FunctionPointer> matches = new ArrayList<FunctionPointer>();
      
      for(FunctionPointer pointer : pointers){
         Function function = pointer.getFunction();
         int mask = function.getModifiers();
         
         if((modifiers & mask) == modifiers) {
            matches.add(pointer);
         }
      }
      return matches;
   }
   
   @Override
   public FunctionPointer resolve(String name, Type... types) throws Exception {
      Object key = builder.create(name, types);
      FunctionPointer pointer = cache.fetch(key);
      
      if(pointer == null) {
         FunctionPointer match = reducer.reduce(pointers, name, types);
         Function function = match.getFunction();
         Signature signature = function.getSignature();

         if(signature.isAbsolute()) {
            cache.cache(key, match);
         }
         return validate(match);
      }
      return validate(pointer);
   }

   @Override
   public FunctionPointer resolve(String name, Object... list) throws Exception {
      Object key = builder.create(name, list);
      FunctionPointer pointer = cache.fetch(key);
      
      if(pointer == null) {
         FunctionPointer match = reducer.reduce(pointers, name, list);
         Function function = match.getFunction();
         Signature signature = function.getSignature();
         
         if(signature.isAbsolute()) {
            cache.cache(key, match);
         }
         return validate(match);
      }
      return validate(pointer);
   }
   
   private FunctionPointer validate(FunctionPointer pointer) {
      Function function = pointer.getFunction();
      Signature signature = function.getSignature();
      Origin origin = signature.getOrigin();
      
      if(!origin.isError()) {
         return pointer;
      }
      return null;
   }
   
   @Override
   public void index(FunctionPointer pointer) throws Exception {
      pointers.add(pointer);
   }
}