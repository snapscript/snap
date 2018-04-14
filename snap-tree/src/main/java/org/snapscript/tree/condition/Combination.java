package org.snapscript.tree.condition;

import static org.snapscript.core.constraint.Constraint.BOOLEAN;
import static org.snapscript.core.variable.BooleanValue.FALSE;
import static org.snapscript.core.variable.BooleanValue.TRUE;

import org.snapscript.core.Evaluation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;
import org.snapscript.parse.StringToken;

public class Combination extends Evaluation {
   
   private final ConditionalOperator operator;
   private final Evaluation right;
   private final Evaluation left;
   
   public Combination(Evaluation left) {
      this(left, null, null);
   }
   
   public Combination(Evaluation left, StringToken operator, Evaluation right) {
      this.operator = ConditionalOperator.resolveOperator(operator);
      this.right = right;
      this.left = left;
   }

   @Override
   public void define(Scope scope) throws Exception { 
      left.define(scope);
      
      if(right != null) {
         right.define(scope);
      }
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint context) throws Exception {
      left.compile(scope, null);
      
      if(right != null) {
         right.compile(scope, null);
      }
      return BOOLEAN;
   }
   
   @Override
   public Value evaluate(Scope scope, Object context) throws Exception { 
      Value first = evaluate(scope, left);
      
      if(first == TRUE) {
         if(operator != null) {
            if(operator.isAnd()) {
               return evaluate(scope, right);
            }
         }
      } else {
         if(operator != null) {
            if(operator.isOr()) {
               return evaluate(scope, right);
            }
         }
      }
      return first;
   }
   
   private Value evaluate(Scope scope, Evaluation evaluation) throws Exception { 
      Value value = evaluation.evaluate(scope, null);
      boolean result = value.getBoolean();
      
      if(result) {
         return TRUE;
      }
      return FALSE;
   } 
}