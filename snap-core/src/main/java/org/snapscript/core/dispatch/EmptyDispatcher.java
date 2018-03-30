package org.snapscript.core.dispatch;

import static org.snapscript.core.Value.NULL;
import static org.snapscript.core.constraint.Constraint.NONE;

import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.constraint.Constraint;

public class EmptyDispatcher implements CallDispatcher {
   
   public EmptyDispatcher() {
      super();
   }

   @Override
   public Constraint compile(Scope scope, Type object, Type... arguments) throws Exception {
      return NONE;
   }

   @Override
   public Value dispatch(Scope scope, Object object, Object... arguments) throws Exception {
      return NULL;
   }
}
