package org.snapscript.tree.operation;

import org.snapscript.core.Value;
import org.snapscript.parse.StringToken;

public enum SignOperator {
   NONE(""){
      @Override
      public Value operate(Number value) {
         NumericConverter converter = NumericConverter.resolveConverter(value);      
         Double decimal = value.doubleValue();
         
         return converter.convert(decimal);
      }      
   },
   PLUS("+"){
      @Override
      public Value operate(Number value) {
         NumericConverter converter = NumericConverter.resolveConverter(value);      
         Double decimal = value.doubleValue();
         
         return converter.convert(+decimal);
      }      
   },
   MINUS("-"){
      @Override
      public Value operate(Number value) { 
         NumericConverter converter = NumericConverter.resolveConverter(value);      
         Double decimal = value.doubleValue();
         
         return converter.convert(-decimal);
      }      
   };
   
   public final String operator;
   
   private SignOperator(String operator){
      this.operator = operator;
   }
   
   public abstract Value operate(Number right);   
   
   public static SignOperator resolveOperator(StringToken token) {
      if(token != null) {
         String value = token.getValue();
         SignOperator[] operators = SignOperator.values();
         
         for(SignOperator operator : operators) {
            if(operator.operator.equals(value)) {
               return operator;
            }
         }
      }
      return NONE;
   }  
}
