package org.snapscript.tree.function;

import org.snapscript.core.Context;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.bind.FunctionBinder;
import org.snapscript.core.function.bind.FunctionMatcher;
import org.snapscript.core.function.dispatch.FunctionDispatcher;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.tree.NameReference;

public class FunctionHolder {

   private NameReference reference;
   private FunctionMatcher group; // this referenced avoids a hash lookup
   
   public FunctionHolder(NameReference reference) {
      this.reference = reference;
   }
   
   public FunctionDispatcher get(Scope scope) throws Exception {
      if(group == null) {
         Module module = scope.getModule();
         Context context = module.getContext();
         FunctionBinder binder = context.getBinder();
         String name = reference.getName(scope);
         
         group = binder.bind(name);
      }
      return group.match(scope);
   }

   public FunctionDispatcher get(Scope scope, Constraint left) throws Exception {
      if(group == null) {
         Module module = scope.getModule();
         Context context = module.getContext();
         FunctionBinder binder = context.getBinder();
         String name = reference.getName(scope);
         
         group = binder.bind(name);
      }
      return group.match(scope, left);
   }
   
   public FunctionDispatcher get(Scope scope, Object left) throws Exception {
      if(group == null) {
         Module module = scope.getModule();
         Context context = module.getContext();
         FunctionBinder binder = context.getBinder();
         String name = reference.getName(scope);
         
         group = binder.bind(name);
      }
      return group.match(scope, left);
   }
}
