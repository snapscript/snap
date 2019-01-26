package org.snapscript.core;

import static org.snapscript.core.constraint.Constraint.NONE;

import java.util.List;

import org.snapscript.common.Consumer;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class PromiseWrapper {

   public PromiseWrapper() {
      super();
   }

   public Promise toPromise(Scope scope, Object object) {
      if(!Promise.class.isInstance(object)) {
         return new IdentityPromise(object);
      }
      return (Promise)object;
   }

   public Object fromPromise(Scope scope, Object object) {
      if(Promise.class.isInstance(object)) {
         Promise promise = (Promise)object;
         return promise.get();
      }
      return object;
   }

   public Constraint fromPromise(Scope scope, Constraint returns) {
      Type type = returns.getType(scope);

      if(type != null) {
         Class real = type.getType();

         if(real != null) {
            if (Promise.class.isAssignableFrom(real)) {
               List<Constraint> generics = returns.getGenerics(scope);

               for (Constraint generic : generics) {
                  return generic;
               }
               return NONE;
            }
         }
      }
      return returns;
   }

   private static class IdentityPromise implements Promise {

      public final Object object;

      private IdentityPromise(Object object) {
         this.object = object;
      }

      @Override
      public Object get() {
         return object;
      }

      @Override
      public Object get(long wait) {
         return object;
      }

      @Override
      public Promise join() {
         return this;
      }

      @Override
      public Promise join(long wait) {
         return this;
      }

      @Override
      public Promise success(Task task) {
         task.execute(object);
         return this;
      }

      @Override
      public Promise failure(Task task) {
         return this;
      }
   }
}
