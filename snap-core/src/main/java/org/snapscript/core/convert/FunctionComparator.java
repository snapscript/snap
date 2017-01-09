package org.snapscript.core.convert;

import static org.snapscript.core.convert.Score.EXACT;
import static org.snapscript.core.convert.Score.INVALID;
import static org.snapscript.core.convert.Score.POSSIBLE;

import java.util.List;

import org.snapscript.core.Type;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.function.Signature;

public class FunctionComparator {
   
   private final ConstraintMatcher matcher;
   
   public FunctionComparator(ConstraintMatcher matcher) {
      this.matcher = matcher;
   }
   
   public Score compare(Function actual, List<Function> require) throws Exception{
      String name = actual.getName();
      
      for(Function function : require) {
         String match = function.getName();
         
         if(name.equals(match)) {
            Score compare = compare(actual, function);
            
            if(compare != INVALID) {
               return compare;
            }
         }
      }
      return INVALID;
   }

   public Score compare(Function actual, Function require) throws Exception{
      Signature actualSignature = actual.getSignature();
      Signature requireSignature = require.getSignature();
      List<Parameter> actualParameters = actualSignature.getParameters();
      List<Parameter> requireParameters = requireSignature.getParameters();
      int actualSize = actualParameters.size();
      int requireSize = requireParameters.size();
      boolean actualVariable = actualSignature.isVariable();
      
      if(actualVariable && actualSize <= requireSize) {
         return compare(actualParameters, requireParameters);
      }
      if(actualSize == requireSize) {
         return compare(actualParameters, requireParameters); // compare(a...) == compare(a, b)
      }
      return INVALID;
   }
   
   private Score compare(List<Parameter> actual, List<Parameter> require) throws Exception{
      int requireSize = require.size();
      
      if(requireSize > 0) {
         Score total = INVALID;
         
         for(int i = 0; i < requireSize; i++) {
            Parameter actualParameter = actual.get(i);
            
            if(actualParameter.isVariable()) { // if variable match remaining
               for(int j = i; j < requireSize; j++) {
                  Parameter requireParameter = require.get(i);
                  Score score = compare(actualParameter, requireParameter);
                  
                  if(score.compareTo(INVALID) <= 0) { // must check for numbers
                     return INVALID;
                  }
                  total = Score.sum(total, score); // sum for better match
               }
               return total;
            }
            Parameter requireParameter = require.get(i);
            Score score = compare(actualParameter, requireParameter);
            
            if(score.compareTo(INVALID) <= 0) { // must check for numbers
               return INVALID;
            }
            total = Score.sum(total, score); // sum for better match
         }
         return total;
      }
      return EXACT;
   }
   
   private Score compare(Parameter actual, Parameter require) throws Exception{
      Type actualType  = actual.getType();
      Type constraintType = require.getType();
      ConstraintConverter converter = matcher.match(constraintType);
      Score score = converter.score(actualType);
      
      if(actual.isVariable()) {
         if(score.compareTo(INVALID) <= 0) {
            return INVALID;
         }
         return POSSIBLE;
      }
      return score;
   }
}
