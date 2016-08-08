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

public class DeclareProperty extends DeclareVariable {

   public DeclareProperty(TextLiteral identifier) {
      this(identifier, null, null);
   }
   
   public DeclareProperty(TextLiteral identifier, Constraint constraint) {      
      this(identifier, constraint, null);
   }
   
   public DeclareProperty(TextLiteral identifier, Evaluation value) {
      this(identifier, null, value);
   }
   
   public DeclareProperty(TextLiteral identifier, Constraint constraint, Evaluation value) {
      super(identifier, constraint, value);
   }
   
   @Override
   protected Value declare(Scope scope, Value value, String name) throws Exception {
      Object object = value.getValue();
      Type type = value.getConstraint();
      State state = scope.getState();
      
      try {      
         Value reference = ValueType.getProperty(object, type);
         state.addVariable(name, reference);
         return reference;
      }catch(Exception e) {
         throw new InternalStateException("Declaration of variable '" + name +"' failed", e);
      }      
   } 
}