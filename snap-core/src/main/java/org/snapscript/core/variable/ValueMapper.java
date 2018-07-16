package org.snapscript.core.variable;

public class ValueMapper {

   public static Character toCharacter(Object value) {
      try {
         return (Character)value;
      } catch(Exception e) {
         return (char)toNumber(value).intValue();
      }
   }
   
   public static Number toNumber(Object value) {
      try {
         return (Number)value;
      } catch(Exception e) {
         return (int)toCharacter(value).charValue();
      }
   }
}
