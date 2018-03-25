package org.snapscript.tree.math;

import org.snapscript.core.Type;

public class NumericChecker {
   
   public static boolean bothNumeric(Type left, Type right){
      Class leftType = left.getType();
      Class rightType = right.getType();
      
      if(leftType != null && rightType != null) {
         return bothNumeric(leftType, rightType);
      }
      return false;
   }

   public static boolean bothNumeric(Class left, Class right){
      if(Number.class.isAssignableFrom(left)) {
         return Number.class.isAssignableFrom(right);
      }
      return false;
   }
   
   public static boolean bothNumeric(Object left, Object right){
      if(Number.class.isInstance(left)) {
         return Number.class.isInstance(right);
      }
      return false;
   }
}