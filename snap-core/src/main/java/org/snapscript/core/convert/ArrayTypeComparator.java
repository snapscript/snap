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

   @Bug("fix convention")
   public Score score(Type constraint, Type actual) throws Exception {
      if(constraint != null && actual != null) {
         Type constraintEntry = constraint.getEntry();
         Type actualEntry = actual.getEntry();
            
         if(constraintEntry != null && actualEntry != null) {
            return score(constraintEntry, actualEntry);
         }
         return checker.cast(actual, constraint); // convention of order is broken
      } 
      return INVALID; 
   }
   
   public Score score(Class constraint, Class actual) throws Exception{
      if(constraint != null && actual != null) {
         if(constraint.isArray() && actual.isArray()) {
            Class constraintEntry = constraint.getComponentType();
            Class actualEntry = actual.getComponentType();
            
            return score(constraintEntry, actualEntry);
         }
         Class constraintType = promoter.promote(constraint);
         Class actualType = promoter.promote(actual);
         
         if(constraintType.equals(actualType)) {
            return EXACT;
         }
         if(constraintType.isAssignableFrom(actualType)) {
            return SIMILAR;
         }
      }
      return INVALID;
   }
}