package org.snapscript.tree.operation;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;
import org.snapscript.core.variable.ValueCache;
import org.snapscript.parse.StringToken;
import org.snapscript.tree.condition.BooleanChecker;
import org.snapscript.tree.math.NumericConverter;

public enum PrefixOperator {
   NOT("!"){
      @Override
      public Value operate(Scope scope, Value right) { 
         Object result = right.getValue();         
         boolean value = !BooleanChecker.isTrue(result);
         
         return ValueCache.getBoolean(scope, value);
      }      
   }, 
   COMPLEMENT("~"){
      @Override
      public Value operate(Scope scope, Value right) {
         Number value = right.getData().getNumber(); 
         NumericConverter converter = NumericConverter.resolveConverter(value);   
         long number = value.longValue();
         
         return converter.convert(scope, ~number);
      }      
   },
   PLUS("+"){
      @Override
      public Value operate(Scope scope, Value right) {
         Number value = right.getData().getNumber(); 
         NumericConverter converter = NumericConverter.resolveConverter(value);   
         double number = value.doubleValue();
         
         return converter.convert(scope, +number);
      }      
   },
   MINUS("-"){
      @Override
      public Value operate(Scope scope, Value right) { 
         Number value = right.getData().getNumber(); 
         NumericConverter converter = NumericConverter.resolveConverter(value);   
         double number = value.doubleValue();
         
         return converter.convert(scope, -number);
      }      
   };
   
   public final String operator;
   
   private PrefixOperator(String operator){
      this.operator = operator;
   }
   
   public abstract Value operate(Scope scope, Value right);   
   
   public static PrefixOperator resolveOperator(StringToken token) {
      if(token != null) {
         String value = token.getValue();
         PrefixOperator[] operators = PrefixOperator.values();
         
         for(PrefixOperator operator : operators) {
            if(operator.operator.equals(value)) {
               return operator;
            }
         }
      }
      return null;
   }  
}