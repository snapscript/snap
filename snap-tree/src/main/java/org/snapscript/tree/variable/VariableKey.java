
package org.snapscript.tree.variable;

import org.snapscript.core.Type;

public class VariableKey {

   private final String name;
   private final Type type;
   
   public VariableKey(String name, Type type) {
      this.name = name;
      this.type = type;
   }
   
   @Override
   public boolean equals(Object key) {
      if(key instanceof VariableKey) {
         return equals((VariableKey)key);
      }
      return false;
   }
   
   public boolean equals(VariableKey key) {
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
      return hash;
   }
   
   @Override
   public String toString() {
      return name;
   }
}
