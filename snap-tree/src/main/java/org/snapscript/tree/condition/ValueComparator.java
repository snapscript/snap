package org.snapscript.tree.condition;

import org.snapscript.core.Value;

public enum ValueComparator {
   NUMERIC {
      @Override
      public int compare(Value left, Value right) {
         Double primary = left.getDouble();
         Double secondary = right.getDouble();

         if(primary != secondary) {      
            if(primary != null && secondary != null) {
               return primary.compareTo(secondary);
            }
            return primary == null ? -1 : 1;
         }
         return 0;
      }
   },
   COMPARABLE{
      @Override
      public int compare(Value left, Value right) {
         Comparable primary = left.getValue();
         Comparable secondary = right.getValue();

         if(primary != secondary) {
            if(primary != null && secondary != null) {
               return primary.compareTo(secondary);
            }
            return primary == null ? -1 : 1;
         }
         return 0;
      }
   },
   OBJECT{
      @Override
      public int compare(Value left, Value right) {
         Object primary = left.getValue();
         Object secondary = right.getValue();

         if(primary != secondary) {
            if(primary != null && secondary != null) {
               if(primary.equals(secondary)) {
                  return 0;
               }
               return -1;
            }
            return primary == null ? -1 : 1;
         }
         return 0;
      }
   };

   public abstract int compare(Value left, Value right);
   
   public static ValueComparator resolveComparator(Value left, Value right) {
      Object primary = left.getValue();
      Object secondary = right.getValue();

      if(primary != null && secondary != null) {
         if (primary instanceof Number && secondary instanceof Number) {
            return NUMERIC;
         }
         if (Comparable.class == primary && Comparable.class == secondary) {
            return COMPARABLE;
         }        
      }
      return OBJECT;
   }

}