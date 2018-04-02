package org.snapscript.tree.operation;

import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.parse.Token;
import org.snapscript.tree.math.NumericChecker;

public abstract class NumericOperation extends Evaluation {
   
   protected final Evaluation evaluation;
   protected final Token operator;
   
   protected NumericOperation(Evaluation evaluation, Token operator) {
      this.evaluation = evaluation;
      this.operator = operator;
   }
   
   @Override
   public void define(Scope scope) throws Exception {
      evaluation.define(scope);
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      Constraint constraint = evaluation.compile(scope, left);
      Type type = constraint.getType(scope);
      
      if(constraint.isConstant()) {
         throw new InternalStateException("Illegal " + operator+ " of constant");
      }
      if(type != null) {
         if(!NumericChecker.isNumeric(type)) {
            throw new InternalStateException("Illegal " + operator +" of type '" + type + "'");
         }
      }
      return constraint;      
   }
}