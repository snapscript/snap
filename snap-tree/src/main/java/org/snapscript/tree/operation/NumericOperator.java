package org.snapscript.tree.operation;

import org.snapscript.core.Value;
import org.snapscript.parse.StringToken;

public enum NumericOperator {
   NONE("", 0) {
      @Override
      public Value operate(Value left, Value right) {
         return right;
      }        
   },
   COALESCE("??", 1){
      @Override
      public Value operate(Value left, Value right) {
         Object first = left.getValue();
         Object second = right.getValue();
         
         return first == null ? right : left;
      }      
   },   
   POWER("**", 2){
      @Override
      public Value operate(Value left, Value right) {
         NumericConverter converter = NumericConverter.resolveConverter(left, right);
         double first = left.getDouble(); 
         double second = right.getDouble();
         double result = Math.pow(first, second);
         
         return converter.convert(result);
      }      
   },   
   DIVIDE("/", 3){
      @Override
      public Value operate(Value left, Value right) {
         NumericConverter converter = NumericConverter.resolveConverter(left, right);
         double first = left.getDouble(); 
         double second = right.getDouble();
         
         return converter.convert(first / second);
      }      
   },
   MULTIPLY("*", 3){
      @Override
      public Value operate(Value left, Value right) {
         NumericConverter converter = NumericConverter.resolveConverter(left, right);
         double first = left.getDouble(); 
         double second = right.getDouble();
         
         return converter.convert(first * second);
      }      
   }, 
   MODULUS("%", 3){
      @Override
      public Value operate(Value left, Value right) {
         NumericConverter converter = NumericConverter.resolveConverter(left, right);
         double first = left.getDouble(); 
         double second = right.getDouble();
         
         return converter.convert(first % second);
      }      
   },   
   PLUS("+", 4){
      @Override
      public Value operate(Value left, Value right) {
         NumericConverter converter = NumericConverter.resolveConverter(left, right);
         double first = left.getDouble(); 
         double second = right.getDouble();
         
         return converter.convert(first + second);
      }      
   }, 
   MINUS("-", 4){
      @Override
      public Value operate(Value left, Value right) {
         NumericConverter converter = NumericConverter.resolveConverter(left, right);
         double first = left.getDouble(); 
         double second = right.getDouble();

         return converter.convert(first - second);
      }      
   },
   SHIFT_RIGHT(">>", 5){
      @Override
      public Value operate(Value left, Value right) {
         NumericConverter converter = NumericConverter.resolveConverter(left, right);
         int first = left.getInteger();
         int second = right.getInteger();
         
         return converter.convert(first >> second); 
      }      
   }, 
   SHIFT_LEFT("<<", 5){
      @Override
      public Value operate(Value left, Value right) {
         NumericConverter converter = NumericConverter.resolveConverter(left, right);
         int first = left.getInteger();
         int second = right.getInteger();
         
         return converter.convert(first << second);  
      }      
   },  
   UNSIGNED_SHIFT_RIGHT(">>>", 5){
      @Override
      public Value operate(Value left, Value right) {
         NumericConverter converter = NumericConverter.resolveConverter(left, right);
         int first = left.getInteger();
         int second = right.getInteger();
         
         return converter.convert(first >>> second); 
      }      
   },
   AND("&", 6){
      @Override
      public Value operate(Value left, Value right) {
         NumericConverter converter = NumericConverter.resolveConverter(left, right);
         int first = left.getInteger();
         int second = right.getInteger();
         
         return converter.convert(first & second);
      }      
   },  
   OR("|", 6){
      @Override
      public Value operate(Value left, Value right) {
         NumericConverter converter = NumericConverter.resolveConverter(left, right);
         int first = left.getInteger();
         int second = right.getInteger();
         
         return converter.convert(first | second); 
      }      
   }, 
   XOR("^", 6){
      @Override
      public Value operate(Value left, Value right) {
         NumericConverter converter = NumericConverter.resolveConverter(left, right);
         int first = left.getInteger();
         int second = right.getInteger();
         
         return converter.convert(first ^ second);   
      }      
   };
   
   public final String operator;
   public final int priority;
   
   private NumericOperator(String operator, int priority) {
      this.priority = -priority; // invert value to sort
      this.operator = operator;
   }   
   
   public abstract Value operate(Value left, Value right);
   
   public static NumericOperator resolveOperator(StringToken token) {
      if(token != null) {
         String value = token.getValue();
         
         for(NumericOperator operator : VALUES) {
            if(operator.operator.equals(value)) {
               return operator;
            }
         }
      }
      return null;
   }   
   
   private static final NumericOperator[] VALUES = {
      PLUS,
      MINUS,
      DIVIDE,
      MULTIPLY,
      MODULUS,
      AND,
      OR,
      XOR,
      SHIFT_LEFT,
      SHIFT_RIGHT,
      UNSIGNED_SHIFT_RIGHT,
      POWER,
      COALESCE,
      NONE
   };
}