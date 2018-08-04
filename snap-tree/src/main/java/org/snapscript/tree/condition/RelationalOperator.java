package org.snapscript.tree.condition;

import org.snapscript.core.convert.InstanceOfChecker;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;
import org.snapscript.core.variable.ValueCache;
import org.snapscript.parse.StringToken;

public enum RelationalOperator {
   SAME("==="){
      @Override
      public Value operate(Scope scope, Value left, Value right) {
         Object first = left.getValue();
         Object second = right.getValue();
         
         if(first == second) {
            return ValueCache.getBoolean(scope, true);
         }
         return ValueCache.getBoolean(scope, false);
      }      
   },   
   NOT_SAME("!=="){
      @Override
      public Value operate(Scope scope, Value left, Value right) {
         Object first = left.getValue();
         Object second = right.getValue();
         
         if(first != second) {
            return ValueCache.getBoolean(scope, true);
         }
         return ValueCache.getBoolean(scope, false);
      }      
   },   
   EQUALS("=="){
      @Override
      public Value operate(Scope scope, Value left, Value right) {
         ValueComparator comparator = ValueComparator.resolveComparator(left, right);
         
         if(comparator.compare(left, right) == 0){
            return ValueCache.getBoolean(scope, true);
         }
         return ValueCache.getBoolean(scope, false);
      }      
   },  
   NOT_EQUALS("!="){
      @Override
      public Value operate(Scope scope, Value left, Value right) {
         ValueComparator comparator = ValueComparator.resolveComparator(left, right);
         
         if(comparator.compare(left, right) != 0){
            return ValueCache.getBoolean(scope, true);
         }
         return ValueCache.getBoolean(scope, false);
      }      
   },  
   GREATER(">"){
      @Override
      public Value operate(Scope scope, Value left, Value right) {
         ValueComparator comparator = ValueComparator.resolveComparator(left, right);
         
         if(comparator.compare(left, right) > 0){
            return ValueCache.getBoolean(scope, true);
         }
         return ValueCache.getBoolean(scope, false);
      }      
   },  
   GREATER_OR_EQUALS(">="){
      @Override
      public Value operate(Scope scope, Value left, Value right) {
         ValueComparator comparator = ValueComparator.resolveComparator(left, right);
         
         if(comparator.compare(left, right) >= 0){
            return ValueCache.getBoolean(scope, true);
         }
         return ValueCache.getBoolean(scope, false);
      }      
   }, 
   LESS("<"){
      @Override
      public Value operate(Scope scope, Value left, Value right) {
         ValueComparator comparator = ValueComparator.resolveComparator(left, right);
         
         if(comparator.compare(left, right) < 0){
            return ValueCache.getBoolean(scope, true);
         }
         return ValueCache.getBoolean(scope, false);
      }      
   }, 
   LESS_OR_EQUALS("<="){
      @Override
      public Value operate(Scope scope, Value left, Value right) {
         ValueComparator comparator = ValueComparator.resolveComparator(left, right);
         
         if(comparator.compare(left, right) <= 0){
            return ValueCache.getBoolean(scope, true);
         }
         return ValueCache.getBoolean(scope, false);
      }      
   },
   INSTANCE_OF("instanceof"){
      @Override
      public Value operate(Scope scope, Value left, Value right) {
         Object first = left.getValue();
         Object second = right.getValue();
         
         if(checker.isInstanceOf(scope, first, second)){
            return ValueCache.getBoolean(scope, true);
         }
         return ValueCache.getBoolean(scope, false);
      }      
   },
   NOT_INSTANCE_OF("!instanceof"){
      @Override
      public Value operate(Scope scope, Value left, Value right) {
         Object first = left.getValue();
         Object second = right.getValue();
         
         if(!checker.isInstanceOf(scope, first, second)){
            return ValueCache.getBoolean(scope, true);
         }
         return ValueCache.getBoolean(scope, false);
      }      
   };
   
   public final InstanceOfChecker checker;
   public final String operator;
   
   private RelationalOperator(String operator) {
      this.checker = new InstanceOfChecker();
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
      NOT_SAME
   };
}