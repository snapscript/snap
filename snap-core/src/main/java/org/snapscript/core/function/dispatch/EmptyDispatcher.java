package org.snapscript.core.function.dispatch;

import static org.snapscript.core.constraint.Constraint.NONE;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class EmptyDispatcher implements FunctionDispatcher {
   
   public EmptyDispatcher() {
      super();
   }

   @Override
   public Constraint compile(Scope scope, Constraint object, Type... arguments) throws Exception {
      return NONE;
   }

   @Override
   public Call dispatch(Scope scope, Object object, Object... arguments) throws Exception {
      return c;
   }
   public static final Call c = new Call();
}
