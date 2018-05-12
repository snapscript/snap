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

   public Score compare(Scope scope, Function actual, Function constraint) throws Exception{
      Signature actualSignature = actual.getSignature();
      Signature constraintSignature = constraint.getSignature();
      List<Parameter> actualParameters = actualSignature.getParameters();
      List<Parameter> constraintParameters = constraintSignature.getParameters();
      int actualSize = actualParameters.size();
      int constraintSize = constraintParameters.size();
      boolean constraintVariable = constraintSignature.isVariable();
      
      if(actualSize == constraintSize) {
         Score score = compare(scope, actualParameters, constraintParameters);
         
         if(score.isValid()) {
            return score;
         }
      }
      if(constraintVariable && constraintSize <= actualSize) {
         Score score = compare(scope, actualParameters, constraintParameters); // compare(a...) == compare(a, b)
         
         if(score.isValid()) {
            return score;
         }
      }
      return INVALID;
   }
   
   private Score compare(Scope scope, List<Parameter> actual, List<Parameter> constraint) throws Exception{
      int actualSize = actual.size();
      
      if(actualSize > 0) {
         Score total = INVALID;
         
         for(int i = 0, j = 0; i < actualSize; i++) {
            Parameter actualParameter = actual.get(i);
            Parameter constraintParameter = constraint.get(j);
            Score score = compare(scope, actualParameter, constraintParameter);
            
            if(score.isInvalid()) { // must check for numbers
               return INVALID;
            }
            total = Score.sum(total, score); // sum for better match
            
            if(!constraintParameter.isVariable()) { // if variable stick
               j++;
            }
         }
         return total;
      }
      return EXACT;
   }
   
   private Score compare(Scope scope, Parameter actual, Parameter constraint) throws Exception{
      Constraint actualConstraint  = actual.getConstraint();
      Constraint constraintConstraint = constraint.getConstraint();
      Type actualType  = actualConstraint.getType(scope);
      Type constraintType = constraintConstraint.getType(scope);
      ConstraintConverter converter = matcher.match(actualType);
      Score score = converter.score(constraintType);
      
      if(constraint.isVariable()) {
         if(score.isInvalid()) {
            return INVALID;
         }
         return POSSIBLE;
      }
      return score;
   }
}