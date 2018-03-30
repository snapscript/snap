package org.snapscript.core.convert;

import static org.snapscript.core.convert.Score.EXACT;
import static org.snapscript.core.convert.Score.INVALID;
import static org.snapscript.core.convert.Score.SIMILAR;

import org.snapscript.core.Bug;
import org.snapscript.core.PrimitivePromoter;
import org.snapscript.core.Type;
import org.snapscript.core.TypeCastChecker;

public class ArrayTypeComparator {
   
   private final PrimitivePromoter promoter;
   private final TypeCastChecker checker;
   
   public ArrayTypeComparator(TypeCastChecker checker){
      this.promoter = new PrimitivePromoter();
      this.checker = checker;
   }

   public Score toArray(Type actual, Type constraint) throws Exception {
      if(constraint != null && actual != null) {
         Type constraintEntry = constraint.getEntry();
         Type actualEntry = actual.getEntry();
            
         if(constraintEntry != null && actualEntry != null) {
            return toArray(actualEntry, constraintEntry);
         }
         if(constraintEntry == null && actualEntry == null) {
            Class constraintType = constraint.getType();
            Class actualType = actual.getType();
            
            if(constraintType != null && actualType != null) {
               return toArray(actualType, constraintType);
            }
            return checker.toType(actual, constraint); // convention of order is broken
         }
      } 
      return INVALID; 
   }
   
   @Bug("better number/primitive checking")
   public Score toArray(Class actual, Class constraint) throws Exception{
      if(constraint != null && actual != null) {
         if(constraint.isArray() && actual.isArray()) {
            Class constraintEntry = constraint.getComponentType();
            Class actualEntry = actual.getComponentType();
            
            return toArray(actualEntry, constraintEntry);
         }
         Class constraintType = promoter.promote(constraint);
         Class actualType = promoter.promote(actual);
         
         if(constraintType.equals(actualType)) {
            return EXACT;
         }
         if(constraintType.isAssignableFrom(actualType)) {
            return SIMILAR;
         }
         if(!Number.class.isAssignableFrom(constraintType)) {
            return INVALID;
         }
         if(Number.class.isAssignableFrom(actualType)) {
            return SIMILAR;
         }
      }
      return INVALID;
   }
}