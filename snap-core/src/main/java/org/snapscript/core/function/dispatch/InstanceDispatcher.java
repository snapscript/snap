package org.snapscript.core.function.dispatch;

import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.search.FunctionSearcher;

public class InstanceDispatcher implements FunctionDispatcher<Object> {
   
   private final AnyDispatcher dispatcher;    
   
   public InstanceDispatcher(FunctionSearcher binder, ErrorHandler handler, String name) {
      this.dispatcher = new AnyDispatcher(binder, handler, name);
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