package org.snapscript.core.function.dispatch;

import static org.snapscript.core.constraint.Constraint.NONE;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;

public class EmptyDispatcher implements FunctionDispatcher {
   
   public EmptyDispatcher() {
      super();
   }

   @Override
   public Constraint compile(Scope scope, Constraint constraint, Type... arguments) throws Exception {
      return NONE;
   }

   @Override
   public Call2 dispatch(Scope scope, Value value, Object... arguments) throws Exception {
      return new Call2(null){
         public Object invoke(Scope scope, Object source, Object... arguments) throws Exception{
            return null;
         }
      };
   }
}      
