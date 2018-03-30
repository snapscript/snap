package org.snapscript.tree.variable;

import org.snapscript.core.Module;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.constraint.Constraint;

public class VariableConstraintWrapper {
   
   public VariableConstraintWrapper() {
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
            return Constraint.getInstance((Value)value);
         }
      }
      return Constraint.getInstance(value);
   }
}
