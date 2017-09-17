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
   
   private final Cache<Object, FunctionCall> cache;
   private final AtomicBoolean constraints;
   private final FunctionKeyBuilder builder;
   private final FunctionSearcher searcher;
   private final List<FunctionCall> group;
   
   public FunctionGroup(FunctionSearcher searcher, FunctionKeyBuilder builder) {
      this.cache = new CopyOnWriteCache<Object, FunctionCall>();
      this.group = new ArrayList<FunctionCall>();
      this.constraints = new AtomicBoolean();
      this.searcher = searcher;
      this.builder = builder;
   }
   
   public FunctionCall resolve(String name, Type... list) throws Exception {
      int size = group.size();
      
      if(constraints.get()) {
         Object key = builder.create(name, list);
         FunctionCall call = cache.fetch(key);
         
         if(call == null) {
            FunctionCall match = searcher.search(group, name, list);
            Function function = match.getFunction();
            Signature signature = function.getSignature();
            
            if(signature.isAbsolute()) {
               cache.cache(key, match);
            }
            return validate(match);
         }
         return validate(call);
      }
      if(size > 0) {
         return group.get(size -1);
      }
      return null;
   }
   
   public FunctionCall resolve(String name, Object... list) throws Exception {
      int size = group.size();
      
      if(constraints.get()) {
         Object key = builder.create(name, list);
         FunctionCall call = cache.fetch(key);
         
         if(call == null) {
            FunctionCall match = searcher.search(group, name, list);
            Function function = match.getFunction();
            Signature signature = function.getSignature();
            
            if(signature.isAbsolute()) {
               cache.cache(key, match);
            }
            return validate(match);
         }
         return validate(call);
      }
      if(size > 0) {
         return group.get(size -1);
      }
      return null;
   }

   public void update(FunctionCall call) {
      Function function = call.getFunction();
      Signature signature = function.getSignature();
      List<Parameter> parameters = signature.getParameters();
      
      for(Parameter parameter : parameters) {
         Type type = parameter.getType();
         
         if(type != null) {
            constraints.set(true);
         }
      }
      group.add(call);
   }
   
   private FunctionCall validate(FunctionCall call) {
      Function function = call.getFunction();
      Signature signature = function.getSignature();
      
      if(!signature.isInvalid()) {
         return call;
      }
      return null;
   }
}
