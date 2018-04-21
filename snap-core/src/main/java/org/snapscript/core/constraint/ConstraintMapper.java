package org.snapscript.core.constraint;

import static org.snapscript.core.ModifierType.CLASS;

import org.snapscript.core.module.Module;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;

public class ConstraintMapper {
   
   public ConstraintMapper() {
      super();
   }

   public Constraint map(Object value) {  
      if(value != null) {
         if(Type.class.isInstance(value)) {
            return Constraint.getConstraint((Type)value, CLASS.mask);
         }
         if(Module.class.isInstance(value)) {
            return Constraint.getConstraint((Module)value);
         }
         if(Value.class.isInstance(value)) {         
            return Constraint.getConstraint((Value)value);
         }
      }
      return Constraint.getConstraint(value);
   }
}
