package org.snapscript.tree.constraint;

import static org.snapscript.core.constraint.Constraint.LIST;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.tree.literal.Literal;

public class ConstraintList extends Literal {

   private final Constraint[] constraints;

   public ConstraintList(Constraint... constraints) {
      this.constraints = constraints;
   }
   
   @Override
   protected LiteralValue create(Scope scope) throws Exception {
      List<Constraint> result = new ArrayList<Constraint>();
      
      for(Constraint constraint : constraints) {
         Type type = constraint.getType(scope);
         
         if(type == null) {
            throw new InternalStateException("Could not find constraint");
         }
         result.add(constraint);
      }
      return new LiteralValue(result, LIST);
   }
}
