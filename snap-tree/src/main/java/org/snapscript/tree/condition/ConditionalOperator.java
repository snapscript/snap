package org.snapscript.tree.condition;

import org.snapscript.parse.StringToken;

public enum ConditionalOperator {
   AND("&&"),
   OR("||");
   
   private final String operator;
   
   private ConditionalOperator(String operator){
      this.operator = operator;
   }
   
  public boolean isAnd() {
     return this == AND;
  }
  
  public boolean isOr() {
     return this == OR;
  }
  
  public static ConditionalOperator resolveOperator(StringToken token){
     if(token != null) {
        String value = token.getValue();
        ConditionalOperator[] operators = ConditionalOperator.values();
        
        for(ConditionalOperator operator : operators) {
           if(operator.operator.equals(value)) {
              return operator;
           }
        }
     }
     return null;
  }
}