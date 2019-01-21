package org.snapscript.tree.operation;

import static org.snapscript.core.variable.Value.NULL;

import org.snapscript.core.Evaluation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;
import org.snapscript.parse.StringToken;

public class Assignment extends Evaluation {

   private final AssignmentOperation operation;
   private final Evaluation left;
   private final Evaluation right;
   
   public Assignment(Evaluation left, StringToken operator, Evaluation right) {
      this.operation = new AssignmentOperation(operator);
      this.left = left;
      this.right = right;
   }
   
   @Override
   public void define(Scope scope) throws Exception { 
      left.define(scope);
      right.define(scope);
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint type) throws Exception { 
      Constraint constraint = left.compile(scope, type);
      
      if(constraint.isConstant()) {
         throw new InternalStateException("Illegal modification of constant");
      }
      return right.compile(scope, type);
   }
   
   @Override
   public Value evaluate(Scope scope, Value context) throws Exception { // this is rubbish
      Value leftResult = left.evaluate(scope, NULL);
      Value rightResult = right.evaluate(scope, NULL);
      
      return operation.operate(scope, leftResult, rightResult);
   }
}