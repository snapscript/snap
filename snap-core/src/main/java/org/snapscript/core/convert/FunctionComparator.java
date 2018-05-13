package org.snapscript.core.convert;

import static org.snapscript.core.convert.Score.EXACT;
import static org.snapscript.core.convert.Score.INVALID;
import static org.snapscript.core.convert.Score.POSSIBLE;

import java.util.List;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.function.Signature;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class FunctionComparator {
   
   private final ConstraintMatcher matcher;
   
   public FunctionComparator(ConstraintMatcher matcher) {
      this.matcher = matcher;
   }

   public Score compare(Scope scope, Function left, Function right) throws Exception{
      Signature leftSignature = left.getSignature();
      Signature rightSignature = right.getSignature();
      List<Parameter> leftParameters = leftSignature.getParameters();
      List<Parameter> rightParameters = rightSignature.getParameters();
      int leftSize = leftParameters.size();
      int rightSize = rightParameters.size();
      boolean leftVariable = leftSignature.isVariable();
      boolean rightVariable = rightSignature.isVariable();
      
      if(leftSize == rightSize) {
         Score score = compare(scope, leftParameters, rightParameters);
         
         if(score.isValid()) {
            return score;
         }
      }
      if(leftVariable && leftSize <= rightSize) {
         Score score = compare(scope, leftParameters, rightParameters); // compare(a...) == compare(a, b)
         
         if(score.isValid()) {
            return score;
         }
      }
      if(rightVariable && rightSize <= leftSize) {
         Score score = compare(scope, rightParameters, leftParameters); // compare(a, b) == compare(a...)
         
         if(score.isValid()) {
            return score;
         }
      }
      return INVALID;
   }
   
   private Score compare(Scope scope, List<Parameter> left, List<Parameter> right) throws Exception{
      int leftSize = left.size();
      
      if(leftSize > 0) {
         Score total = INVALID;
         
         for(int i = 0, j = 0; i < leftSize; i++) {
            Parameter leftParameter = left.get(i);
            Parameter rightParameter = right.get(j);
            Score score = compare(scope, leftParameter, rightParameter);
            
            if(score.isInvalid()) { // must check for numbers
               return INVALID;
            }
            total = Score.sum(total, score); // sum for better match
            
            if(!leftParameter.isVariable()) { // if variable stick
               j++;
            }
         }
         return total;
      }
      return EXACT;
   }
   
   private Score compare(Scope scope, Parameter left, Parameter right) throws Exception{
      Constraint leftConstraint  = left.getConstraint();
      Constraint rightConstraint = right.getConstraint();
      Type leftType  = leftConstraint.getType(scope);
      Type rightType = rightConstraint.getType(scope);
      ConstraintConverter converter = matcher.match(leftType);
      Score score = converter.score(rightType);
      
      if(left.isVariable()) {
         if(score.isInvalid()) {
            return INVALID;
         }
         return POSSIBLE;
      }
      return score;
   }
}