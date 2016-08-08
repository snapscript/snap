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

public class DeclareConstant extends DeclareVariable {

   public DeclareConstant(TextLiteral identifier) {
      this(identifier, null, null);
   }
   
   public DeclareConstant(TextLiteral identifier, Constraint constraint) {      
      this(identifier, constraint, null);
   }
   
   public DeclareConstant(TextLiteral identifier, Evaluation value) {
      this(identifier, null, value);
   }
   
   public DeclareConstant(TextLiteral identifier, Constraint constraint, Evaluation value) {
      super(identifier, constraint, value);
   }
   
   @Override
   protected Value declare(Scope scope, Value value, String name) throws Exception {
      Object object = value.getValue();
      Type type = value.getConstraint();
      State state = scope.getState();
      
      try {      
         Value constant = ValueType.getConstant(object, type);
         state.addVariable(name, constant);
         return constant;
      }catch(Exception e) {
         throw new InternalStateException("Declaration of constant '" + name +"' failed", e);
      }      
   }  
}