package org.snapscript.core.convert;

import org.snapscript.core.PrimitivePromoter;

public class ArrayTypeComparator {
   
   private final PrimitivePromoter promoter;
   
   public ArrayTypeComparator(){
      this.promoter = new PrimitivePromoter();
   }

   public boolean isEqual(Class require, Class actual) {
      if(require.isArray() && actual.isArray()) {
         Class requireEntry = require.getComponentType();
         Class actualEntry = actual.getComponentType();
         
         return isEqual(requireEntry, actualEntry);
      }
      Class requireType = promoter.promote(require);
      Class actualType = promoter.promote(actual);
      
      return requireType == actualType;
   }
}
