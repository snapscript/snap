package org.snapscript.tree.dispatch;

import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.tree.NameReference;

public class InstanceDispatcher implements InvocationDispatcher<Object> {
   
   private final ScopeDispatcher dispatcher;    
   
   public InstanceDispatcher(NameReference reference) {
      this.dispatcher = new ScopeDispatcher(reference);
   }

   @Override
   public Value dispatch(Scope scope, Object object, Object... arguments) throws Exception {
      return dispatcher.dispatch(scope, scope, arguments);         
   }
}