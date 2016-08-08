package org.snapscript.core;

public class ValueKey {

   private final String name;
   private final Type type;
   
   public ValueKey(String name, Type type) {
      this.name = name;
      this.type = type;
   }
   
   @Override
   public boolean equals(Object key) {
      if(key instanceof ValueKey) {
         return equals((ValueKey)key);
      }
      return false;
   }
   
   public boolean equals(ValueKey key) {
      if(key.type != type) {
         return false;
      }
      return key.name.equals(name);
   }
   
   @Override
   public int hashCode() {
      int hash = name.hashCode();
      
      if(type != null) {
         return 31 * hash+type.hashCode();
      }
      return name.hashCode();
   }
   
   @Override
   public String toString() {
      return name;
   }
}
