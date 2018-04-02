package org.snapscript.tree.reference;

import static org.snapscript.core.ModifierType.CONSTANT;

import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.Value;
import org.snapscript.core.type.Type;

public class TypeReference extends Evaluation {
   
   private Evaluation[] list;
   private Value type;
   
   public TypeReference(Evaluation... list) {
      this.list = list;
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      Value value = evaluate(scope, null);
      Type type = value.getValue();
      
      return Constraint.getConstraint(type, CONSTANT.mask);
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      if(type == null) {
         for(Evaluation part : list) {
            Value value = part.evaluate(scope, left);
            
            if(value == null) {
               throw new InternalStateException("Could not determine type");
            }
            left = value.getValue();
         }
         type = Value.getTransient(left);
      }
      return type;
   }

}