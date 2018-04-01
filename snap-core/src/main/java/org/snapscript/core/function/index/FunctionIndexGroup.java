package org.snapscript.core.function.index;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;
import org.snapscript.core.type.Type;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.function.Signature;
import org.snapscript.core.function.search.FunctionPointer;
import org.snapscript.core.function.search.FunctionScanner;

public class FunctionIndexGroup {
   
   private final Cache<Object, FunctionPointer> cache;
   private final List<FunctionPointer> group;
   private final AtomicBoolean constraints;
   private final FunctionKeyBuilder builder;
   private final FunctionScanner searcher;
   private final String name;
   
   public FunctionIndexGroup(FunctionScanner searcher, FunctionKeyBuilder builder, String name) {
      this.cache = new CopyOnWriteCache<Object, FunctionPointer>();
      this.group = new ArrayList<FunctionPointer>();
      this.constraints = new AtomicBoolean();
      this.searcher = searcher;
      this.builder = builder;
      this.name = name;
   }
   
   public FunctionPointer resolve(Type... list) throws Exception {
      int size = group.size();
      
      if(constraints.get()) {
         Object key = builder.create(name, list);
         FunctionPointer pointer = cache.fetch(key);
         
         if(pointer == null) {
            FunctionPointer match = searcher.scan(group, name, list);
            Function function = match.getFunction();
            Signature signature = function.getSignature();
            
            if(signature.isAbsolute()) {
               cache.cache(key, match);
            }
            return validate(match);
         }
         return validate(pointer);
      }
      if(size > 0) {
         return group.get(size -1);
      }
      return null;
   }
   
   public FunctionPointer resolve(Object... list) throws Exception {
      int size = group.size();
      
      if(constraints.get()) {
         Object key = builder.create(name, list);
         FunctionPointer pointer = cache.fetch(key);
         
         if(pointer == null) {
            FunctionPointer match = searcher.scan(group, name, list);
            Function function = match.getFunction();
            Signature signature = function.getSignature();
            
            if(signature.isAbsolute()) {
               cache.cache(key, match);
            }
            return validate(match);
         }
         return validate(pointer);
      }
      if(size > 0) {
         return group.get(size -1);
      }
      return null;
   }

   public void index(FunctionPointer pointer) {
      Function function = pointer.getFunction();
      Signature signature = function.getSignature();
      List<Parameter> parameters = signature.getParameters();
      
      for(Parameter parameter : parameters) {
         Constraint constraint = parameter.getType();
         Type type = constraint.getType(null);
         
         if(type != null) {
            constraints.set(true);
         }
      }
      group.add(pointer);
   }
   
   private FunctionPointer validate(FunctionPointer pointer) {
      Function function = pointer.getFunction();
      Signature signature = function.getSignature();
      
      if(!signature.isInvalid()) {
         return pointer;
      }
      return null;
   }
}