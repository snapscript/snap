package org.snapscript.core.bind;

public class FunctionKey {      

   private final Object[] types;
   private final String function;
   private final Object source;
   private final String type;
   
   public FunctionKey(Object source, String type, String function, Object[] types) {
      this.function = function;
      this.source = source;
      this.types = types;
      this.type = type;
   }
   
   @Override
   public boolean equals(Object key) {
      if(key instanceof FunctionKey) {
         return equals((FunctionKey)key);
      }
      return false;
   }
   
   public boolean equals(FunctionKey key) {
      if(key.source != source) {
         return false;
      }
      if(key.types.length != types.length) {
         return false;
      }
      for(int i = 0; i < types.length; i++) {
         if(types[i] != key.types[i]) {
            return false;
         }         
      }
      return key.type.equals(type);
   }
   
   @Override
   public int hashCode() {
      int hash = types.length;
      
      hash = hash *31 + type.hashCode();
      hash = hash *31 + function.hashCode();
      
      return hash;
   }
   
   @Override
   public String toString() {
      return type;
   }
}
