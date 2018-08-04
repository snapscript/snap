package org.snapscript.tree.condition;

import org.snapscript.core.variable.Value;

public enum ValueComparator {
   NUMERIC_NUMERIC {
      @Override
      public int compare(Value left, Value right) {
         double primary = left.getData().getDouble();
         double secondary = right.getData().getDouble();

         return Double.compare(primary, secondary);
      }
   },
   NUMERIC_CHARACTER {
      @Override
      public int compare(Value left, Value right) {
         int primary = left.getData().getInteger();
         char secondary = right.getData().getCharacter();
 
         return Integer.compare(primary, secondary);
      }
   },
   CHARACTER_NUMERIC {
      @Override
      public int compare(Value left, Value right) {
         char primary = left.getData().getCharacter();
         int secondary = right.getData().getInteger();
     
         return Integer.compare(primary, secondary);
      }
   },
   STRING_CHARACTER {
      @Override
      public int compare(Value left, Value right) {
         String primary = left.getData().getString();
         int length = primary.length();
         
         if(length > 0) {
            char secondary = right.getData().getCharacter();
            char value = primary.charAt(0);
            
            return Character.compare(value, secondary);
         }
         return -1;
      }
   },
   CHARACTER_STRING {
      @Override
      public int compare(Value left, Value right) {
         String secondary = right.getData().getString();
         int length = secondary.length();
         
         if(length > 0) {
            char primary = left.getData().getCharacter();
            char value = secondary.charAt(0);
            
            return Character.compare(primary, value);
         }
         return 1;
      }
   },
   COMPARABLE_COMPARABLE{
      @Override
      public int compare(Value left, Value right) {
         Comparable primary = left.getData().getValue();
         Comparable secondary = right.getData().getValue();

         return primary.compareTo(secondary);
      }
   },
   OBJECT_OBJECT{
      @Override
      public int compare(Value left, Value right) {
         Object primary = left.getData().getValue();
         Object secondary = right.getData().getValue();

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
      Object primary = left.getData().getValue();
      Object secondary = right.getData().getValue();

      if(primary != null && secondary != null) {
         if(Number.class.isInstance(primary)) {
            if(Number.class.isInstance(secondary)) {
               return NUMERIC_NUMERIC;
            }
            if(Character.class.isInstance(secondary)) {
               return NUMERIC_CHARACTER;
            }
            return OBJECT_OBJECT;
         }
         if(Character.class.isInstance(primary)) {
            if(Number.class.isInstance(secondary)) {
               return CHARACTER_NUMERIC;
            }
            if(String.class.isInstance(secondary)) {
               return CHARACTER_STRING;
            }
            if(Character.class.isInstance(secondary)) {
               return COMPARABLE_COMPARABLE;
            }
            return OBJECT_OBJECT;
         }      
         if(String.class.isInstance(primary)) {
            if(String.class.isInstance(secondary)) {
               return COMPARABLE_COMPARABLE;
            }
            if(Character.class.isInstance(secondary)) {
               return STRING_CHARACTER;
            }
            return OBJECT_OBJECT;
         }
         if(Comparable.class.isInstance(primary)) {
            if(Comparable.class.isInstance(secondary)) {
               return COMPARABLE_COMPARABLE;
            }
         }
      }
      return OBJECT_OBJECT;
   }

}