package org.snapscript.tree.function;

import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.dispatch.FunctionBinder;
import org.snapscript.core.function.dispatch.FunctionDispatcher;
import org.snapscript.core.function.dispatch.FunctionGroup;
import org.snapscript.tree.NameReference;

public class FunctionHolder {

   private NameReference reference;
   private FunctionGroup group; // this referenced avoids a hash lookup
   
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
      return group.get(scope);
   }

   public FunctionDispatcher get(Scope scope, Constraint left) throws Exception {
      if(group == null) {
         Module module = scope.getModule();
         Context context = module.getContext();
         FunctionBinder binder = context.getBinder();
         String name = reference.getName(scope);
         
         group = binder.bind(name);
      }
      return group.get(scope, left);
   }
   
   public FunctionDispatcher get(Scope scope, Object left) throws Exception {
      if(group == null) {
         Module module = scope.getModule();
         Context context = module.getContext();
         FunctionBinder binder = context.getBinder();
         String name = reference.getName(scope);
         
         group = binder.bind(name);
      }
      return group.get(scope, left);
   }
}
