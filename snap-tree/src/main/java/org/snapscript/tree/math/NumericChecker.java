package org.snapscript.tree.math;

public class NumericChecker {

   public static boolean bothNumeric(Object left, Object right){
      return left instanceof Number && right instanceof Number;
   }
}