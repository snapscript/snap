package org.snapscript.core.dispatch;

import org.snapscript.core.Constraint;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.core.error.ErrorHandler;

public class InstanceDispatcher implements CallDispatcher<Object> {
   
   private final DynamicDispatcher dispatcher;    
   
   public InstanceDispatcher(FunctionBinder binder, ErrorHandler handler, String name) {
      this.dispatcher = new DynamicDispatcher(binder, handler, name);
   }

   @Override
   public Constraint compile(Scope scope, Type object, Type... arguments) throws Exception {
      Type type = scope.getType();
      return dispatcher.compile(scope, type, arguments);       // args wrong  
   }
   
   @Override
   public Value dispatch(Scope scope, Object object, Object... arguments) throws Exception {
      return dispatcher.dispatch(scope, scope, arguments);         
   }
}