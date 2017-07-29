package org.snapscript.tree.dispatch;

import org.snapscript.core.Scope;
import org.snapscript.core.Type;

public class SuperInvocationBinder extends InvocationBinder {

   private final Type type;
   
   public SuperInvocationBinder(Type type) {
      this.type = type;
   }
   
   @Override
   public InvocationDispatcher bind(Scope scope, Object left) {
      Class base = type.getType();
      
      if(base != null) {
         return new BridgeDispatcher(scope, type); // native java object
      }
      return super.bind(scope, left);
   }
}
