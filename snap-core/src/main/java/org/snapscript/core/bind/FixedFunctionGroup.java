package org.snapscript.core.bind;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;
import org.snapscript.core.Type;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.function.Signature;

public class FixedFunctionGroup implements FunctionGroup {
   
   private final Cache<Object, Function> cache;
   private final AtomicBoolean constraints;
   private final FunctionKeyBuilder builder;
   private final FunctionSearcher searcher;
   private final List<Function> group;
   private final int filter;
   
   public FixedFunctionGroup(FunctionSearcher searcher, FunctionKeyBuilder builder, int filter) {
      this.cache = new CopyOnWriteCache<Object, Function>();
      this.group = new ArrayList<Function>();
      this.constraints = new AtomicBoolean();
      this.searcher = searcher;
      this.builder = builder;
      this.filter = filter;
   }
   
   @Override
   public Function resolve(String name, Type... list) throws Exception {
      int size = group.size();
      
      if(constraints.get()) {
         Object key = builder.create(name, list);
         Function function = cache.fetch(key);
         
         if(function == null) {
            Function match = searcher.search(group, list);
            Signature signature = match.getSignature();
            
            if(signature.isAbsolute()) {
               cache.cache(key, match);
            }
            return resolve(match);
         }
         return resolve(function);
      }
      if(size > 0) {
         Function function = group.get(size -1);
         int modifiers = function.getModifiers();
         
         if((modifiers & filter) == 0) {
            return function;
         }
      }
      return null;
   }
   
   @Override
   public Function resolve(String name, Object... list) throws Exception {
      int size = group.size();
      
      if(constraints.get()) {
         Object key = builder.create(name, list);
         Function function = cache.fetch(key);
         
         if(function == null) {
            Function match = searcher.search(group, list);
            Signature signature = match.getSignature();
            
            if(signature.isAbsolute()) {
               cache.cache(key, match);
            }
            return resolve(match);
         }
         return resolve(function);
      }
      if(size > 0) {
         Function function = group.get(size -1);
         int modifiers = function.getModifiers();
         
         if((modifiers & filter) == 0) {
            return function;
         }
      }
      return null;
   }

   @Override
   public void update(Function function) {
      Signature signature = function.getSignature();
      List<Parameter> parameters = signature.getParameters();
      
      for(Parameter parameter : parameters) {
         Type type = parameter.getType();
         
         if(type != null) {
            constraints.set(true);
         }
      }
      group.add(function);
   }
   
   private Function resolve(Function function) {
      Signature signature = function.getSignature();
      
      if(!signature.isInvalid()) {
         return function;
      }
      return null;
   }
}
