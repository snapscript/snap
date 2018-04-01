package org.snapscript.tree.condition;

import org.snapscript.core.scope.BooleanValue;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.Value;
import org.snapscript.parse.StringToken;

public enum RelationalOperator {
   SAME("==="){
      @Override
      public Value operate(Scope scope, Value left, Value right) {
         Object first = left.getValue();
         Object second = right.getValue();
         
         if(first == second) {
            return BooleanValue.TRUE;
         }
         return BooleanValue.FALSE;
      }      
   },   
   NOT_SAME("!=="){
      @Override
      public Value operate(Scope scope, Value left, Value right) {
         Object first = left.getValue();
         Object second = right.getValue();
         
         if(first != second) {
            return BooleanValue.TRUE;
         }
         return BooleanValue.FALSE;
      }      
   },   
   EQUALS("=="){
      @Override
      public Value operate(Scope scope, Value left, Value right) {
         ValueComparator comparator = ValueComparator.resolveComparator(left, right);
         
         if(comparator.compare(left, right) == 0){
            return BooleanValue.TRUE;
         }
         return BooleanValue.FALSE;
      }      
   },  
   NOT_EQUALS("!="){
      @Override
      public Value operate(Scope scope, Value left, Value right) {
         ValueComparator comparator = ValueComparator.resolveComparator(left, right);
         
         if(comparator.compare(left, right) != 0){
            return BooleanValue.TRUE;
         }
         return BooleanValue.FALSE;
      }      
   },  
   GREATER(">"){
      @Override
      public Value operate(Scope scope, Value left, Value right) {
         ValueComparator comparator = ValueComparator.resolveComparator(left, right);
         
         if(comparator.compare(left, right) > 0){
            return BooleanValue.TRUE;
         }
         return BooleanValue.FALSE;
      }      
   },  
   GREATER_OR_EQUALS(">="){
      @Override
      public Value operate(Scope scope, Value left, Value right) {
         ValueComparator comparator = ValueComparator.resolveComparator(left, right);
         
         if(comparator.compare(left, right) >= 0){
            return BooleanValue.TRUE;
         }
         return BooleanValue.FALSE;
      }      
   }, 
   LESS("<"){
      @Override
      public Value operate(Scope scope, Value left, Value right) {
         ValueComparator comparator = ValueComparator.resolveComparator(left, right);
         
         if(comparator.compare(left, right) < 0){
            return BooleanValue.TRUE;
         }
         return BooleanValue.FALSE;
      }      
   }, 
   LESS_OR_EQUALS("<="){
      @Override
      public Value operate(Scope scope, Value left, Value right) {
         ValueComparator comparator = ValueComparator.resolveComparator(left, right);
         
         if(comparator.compare(left, right) <= 0){
            return BooleanValue.TRUE;
         }
         return BooleanValue.FALSE;
      }      
   },
   MATCH("=~"){
      @Override
      public Value operate(Scope scope, Value left, Value right) {
         Object first = left.getValue();
         Object second = right.getValue();
         
         if(checker.isMatch(scope, first, second)){
            return BooleanValue.TRUE;
         }
         return BooleanValue.FALSE;
      }      
   },
   NOT_MATCH("!=~"){
      @Override
      public Value operate(Scope scope, Value left, Value right) {
         Object first = left.getValue();
         Object second = right.getValue();
         
         if(!checker.isMatch(scope, first, second)){
            return BooleanValue.TRUE;
         }
         return BooleanValue.FALSE;
      }      
   },
   INSTANCE_OF("instanceof"){
      @Override
      public Value operate(Scope scope, Value left, Value right) {
         Object first = left.getValue();
         Object second = right.getValue();
         
         if(checker.isInstance(scope, first, second)){
            return BooleanValue.TRUE;
         }
         return BooleanValue.FALSE;
      }      
   },
   NOT_INSTANCE_OF("!instanceof"){
      @Override
      public Value operate(Scope scope, Value left, Value right) {
         Object first = left.getValue();
         Object second = right.getValue();
         
         if(!checker.isInstance(scope, first, second)){
            return BooleanValue.TRUE;
         }
         return BooleanValue.FALSE;
      }      
   };
   
   public final RelationalChecker checker;
   public final String operator;
   
   private RelationalOperator(String operator) {
      this.checker = new RelationalChecker();
      this.operator = operator;
   }
   
   public abstract Value operate(Scope scope, Value left, Value right);
   
   public static RelationalOperator resolveOperator(StringToken token) {
      if(token != null) {
         String value = token.getValue();

         for(RelationalOperator operator : VALUES) {
            if(operator.operator.equals(value)) {
               return operator;
            }
         }
      }
      return null;
   }   
   
   private static final RelationalOperator[] VALUES = {
      EQUALS,
      NOT_EQUALS,
      LESS,
      GREATER,
      LESS_OR_EQUALS,
      GREATER_OR_EQUALS,
      INSTANCE_OF,
      NOT_INSTANCE_OF,
      SAME,
      NOT_SAME,
      MATCH,
      NOT_MATCH
   };
}