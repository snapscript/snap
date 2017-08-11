package org.snapscript.tree.condition;

import org.snapscript.core.Value;

public enum ValueComparator {
   NUMERIC_NUMERIC {
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
   NUMERIC_CHARACTER {
      @Override
      public int compare(Value left, Value right) {
         Integer primary = left.getInteger();
         Character secondary = right.getCharacter();
 
         if(primary != null && secondary != null) {
            Integer value = Character.getNumericValue(secondary);
            return primary.compareTo(value);
         }
         return primary == null ? -1 : 1;
      }
   },
   CHARACTER_NUMERIC {
      @Override
      public int compare(Value left, Value right) {
         Character primary = left.getCharacter();
         Integer secondary = right.getInteger();
     
         if(primary != null && secondary != null) {
            Integer value = Character.getNumericValue(primary);
            return value.compareTo(secondary);
         }
         return primary == null ? -1 : 1;
      }
   },
   STRING_CHARACTER {
      @Override
      public int compare(Value left, Value right) {
         String primary = left.getString();
         Character secondary = right.getCharacter();
   
         if(primary != null && secondary != null) {
            int length = primary.length();
            
            if(length > 0) {
               Character value = primary.charAt(0);
               return secondary.compareTo(value);
            }
            return -11;
         }
         return primary == null ? -1 : 1;
      }
   },
   CHARACTER_STRING {
      @Override
      public int compare(Value left, Value right) {
         Character primary = left.getCharacter();
         String secondary = right.getString();
    
         if(primary != null && secondary != null) {
            int length = secondary.length();
            
            if(length > 0) {
               Character value = secondary.charAt(0);
               return primary.compareTo(value);
            }
            return 1;
         }
         return primary == null ? -1 : 1;
      }
   },
   COMPARABLE_COMPARABLE{
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
   OBJECT_OBJECT{
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