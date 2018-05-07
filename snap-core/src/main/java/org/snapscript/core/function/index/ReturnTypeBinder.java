package org.snapscript.core.function.index;

import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.function.Function;
import org.snapscript.core.scope.Scope;

public class ReturnTypeBinder {
   
   private final AtomicReference<ReturnType> reference;
   private final ReturnTypeBuilder resolver;
   
   public ReturnTypeBinder(Function function) {
      this.reference = new AtomicReference<ReturnType>();
      this.resolver = new ReturnTypeBuilder(function); 
   }
   
   public ReturnType bind(Scope scope) {
      ReturnType type = reference.get();
      
      if(type == null) {
         type = resolver.create(scope);
         reference.set(type);
      }
      return type;
   }



}
