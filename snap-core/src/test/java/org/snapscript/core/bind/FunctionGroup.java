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

public class FunctionGroup {
   
   private final Cache<Object, Function> cache;
   private final AtomicBoolean constraints;
   private final FunctionKeyBuilder builder;
   private final FunctionMatcher matcher;
   private final List<Function> group;
   
   public FunctionGroup(FunctionMatcher matcher, FunctionKeyBuilder builder) {
      this.cache = new CopyOnWriteCache<Object, Function>();
      this.group = new ArrayList<Function>();
      this.constraints = new AtomicBoolean();
      this.matcher = matcher;
      this.builder = builder;
   }
   
   public Function resolve(String name, Type... list) throws Exception {
      if(constraints.get()) {
         Object key = builder.create(name, list);
         Function function = cache.fetch(key);
         
         if(function == null) {
            Function match = matcher.match(group, list);
            
            if(match != null) {
               cache.cache(key, match);
               return match;
            }
         }
         return function;
      }
      if(!group.isEmpty()) {
         return group.get(0);
      }
      return null;
   }
   
   public Function resolve(String name, Object... list) throws Exception {
      if(constraints.get()) {
         Object key = builder.create(name, list);
         Function function = cache.fetch(key);
         
         if(function == null) {
            Function match = matcher.match(group, list);
            
            if(match != null) {
               cache.cache(key, match);
               return match;
            }
         }
         return function;
      }
      if(!group.isEmpty()) {
         return group.get(0);
      }
      return null;
   }

   public void update(Function function) {
      Signature signature = function.getSignature();
      List<Parameter> parameters = signature.getParameters();
      
      for(Parameter parameter : parameters) {
         Type type = parameter.getType();
         
         if(type != null) {
            constraints.set(true);
         }
      }
   }
}
