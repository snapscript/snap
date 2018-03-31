package org.snapscript.tree.variable;

import org.snapscript.core.Module;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.constraint.Constraint;

public class VariableConstraintMapper {
   
   public VariableConstraintMapper() {
      super();
   }

   public Constraint toConstraint(Object value) {  
      if(value != null) {
         if(Type.class.isInstance(value)) {
            return Constraint.getStatic((Type)value);
         }
         if(Module.class.isInstance(value)) {
            return Constraint.getModule((Module)value);
         }
         if(Value.class.isInstance(value)) {         
            return Constraint.getVariable((Value)value);
         }
      }
      return Constraint.getVariable(value);
   }
}
