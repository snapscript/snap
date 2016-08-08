package org.snapscript.tree.operation;

import static org.snapscript.core.convert.Score.INVALID;

import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.convert.ConstraintConverter;
import org.snapscript.core.convert.ConstraintMatcher;
import org.snapscript.core.convert.Score;
import org.snapscript.parse.StringToken;

public enum AssignmentOperator {
   EQUAL(NumericOperator.NONE, "="), 
   PLUS_EQUAL(NumericOperator.PLUS, "+="), 
   MINUS_EQUAL(NumericOperator.MINUS, "-="), 
   POWER_EQUAL(NumericOperator.POWER, "**="),    
   MLTIPLY_EQUAL(NumericOperator.MULTIPLY, "*="), 
   MODULUS_EQUAL(NumericOperator.MODULUS, "%="), 
   DIVIDE_EQUAL(NumericOperator.DIVIDE,"/="),  
   AND_EQUAL(NumericOperator.AND, "&="),    
   OR_EQUAL(NumericOperator.OR, "|="),
   XOR_EQUAL(NumericOperator.XOR, "^="),    
   SHIFT_RIGHT_EQUAL(NumericOperator.SHIFT_RIGHT, ">>="),
   SHIFT_LEFT_EQUAL(NumericOperator.SHIFT_LEFT, "<<="),
   UNSIGNED_SHIFT_RIGHT_EQUAL(NumericOperator.UNSIGNED_SHIFT_RIGHT, ">>>=");
   
   private final NumericOperator operator;
   private final String symbol;
   
   private AssignmentOperator(NumericOperator operator, String symbol) {
      this.operator = operator;
      this.symbol = symbol;
   }
   
   public Value operate(Scope scope, Value left, Value right) throws Exception {
      Type type = left.getConstraint();
      Value result = operator.operate(left, right);
      Object value = result.getValue();
      
      if(type != null) {
         Module module = scope.getModule();
         Context context = module.getContext();
         ConstraintMatcher matcher = context.getMatcher();
         ConstraintConverter converter = matcher.match(type);
         Score score = converter.score(value);
         
         if(score.compareTo(INVALID) == 0) {
            throw new InternalStateException("Illegal assignment to variable of type '" + type + "'");
         }
         if(value != null) {
            value = converter.convert(value);
         }
      }
      left.setValue(value);
      return left;
   }
   
   public static AssignmentOperator resolveOperator(StringToken token) {
      if(token != null) {
         String value = token.getValue();
         AssignmentOperator[] operators = AssignmentOperator.values();
         
         for(AssignmentOperator operator : operators) {
            if(operator.symbol.equals(value)) {
               return operator;
            }
         }
      }
      return null;
   }
}
