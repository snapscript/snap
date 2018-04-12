package org.snapscript.core.function.dispatch;

import static org.snapscript.core.constraint.Constraint.NONE;
import static org.snapscript.core.scope.Value.NULL;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.Value;
import org.snapscript.core.type.Type;
import org.snapscript.core.constraint.Constraint;

public class EmptyDispatcher implements FunctionDispatcher {
   
   public EmptyDispatcher() {
      super();
   }

   @Override
   public Constraint compile(Scope scope, Type object, Type... arguments) throws Exception {
      return NONE;
   }

   @Override
   public Value evaluate(Scope scope, Object object, Object... arguments) throws Exception {
      return NULL;
   }
}
