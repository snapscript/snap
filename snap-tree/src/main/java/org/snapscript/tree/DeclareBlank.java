package org.snapscript.tree;

import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.tree.constraint.Constraint;
import org.snapscript.tree.literal.TextLiteral;

public class DeclareBlank extends DeclareVariable {

   public DeclareBlank(TextLiteral identifier) {
      super(identifier, null, null);
   }
   
   public DeclareBlank(TextLiteral identifier, Constraint constraint) {      
      super(identifier, constraint, null);
   }
   
   public DeclareBlank(TextLiteral identifier, Evaluation value) {
      this(identifier, null, value);
   }
   
   public DeclareBlank(TextLiteral identifier, Constraint constraint, Evaluation value) {
      super(identifier, constraint, value);
   }
   
   @Override
   protected Value declare(Scope scope, Value value, String name) throws Exception {
      Object object = value.getValue();
      Type type = value.getConstraint();
      State state = scope.getState();
      
      try {      
         Value blank = ValueType.getBlank(type);
         blank.setValue(object); // this should be null
         state.addVariable(name, blank);
         return blank;
      }catch(Exception e) {
         throw new InternalStateException("Declaration of constant '" + name +"' failed", e);
      }      
   }  
}