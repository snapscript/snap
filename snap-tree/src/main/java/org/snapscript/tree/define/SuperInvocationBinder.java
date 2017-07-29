package org.snapscript.tree.define;

import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.tree.dispatch.InvocationBinder;
import org.snapscript.tree.dispatch.InvocationDispatcher;

public class SuperInvocationBinder extends InvocationBinder {

   private final Type type;
   
   public SuperInvocationBinder(Type type) {
      this.type = type;
   }
   
   @Override
   public InvocationDispatcher bind(Scope scope, Object left) {
      Class base = type.getType();
      
      if(base != null) {
         return new SuperDispatcher(scope, type); // native java object
      }
      return super.bind(scope, left);
   }
}
