package org.snapscript.tree.define;

import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.tree.NameReference;
import org.snapscript.tree.dispatch.InvocationBinder;
import org.snapscript.tree.dispatch.InvocationDispatcher;

public class SuperInvocationBinder {

   private final InvocationBinder binder;
   private final Type type;
   
   public SuperInvocationBinder(NameReference reference, Type type) {
      this.binder = new InvocationBinder(reference);
      this.type = type;
   }
   
   public InvocationDispatcher bind(Scope scope, Object left) {
      Class base = type.getType();
      
      if(base != null) {
         return new SuperDispatcher(type); // native java object
      }
      return binder.bind(scope, left);
   }
}