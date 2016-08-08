package org.snapscript.core.bind;

public class FunctionKey {      

   private final Object[] types;
   private final String function;
   private final Object source;
   private final String name;
   private final int length;
   
   public FunctionKey(Object source, String function, Object[] types) {
      this.name = source.toString();
      this.length = name.length();
      this.function = function;
      this.source = source;
      this.types = types;
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
      if(key.length != length) {
         return false;
      }
      for(int i = 0; i < types.length; i++) {
         if(types[i] != key.types[i]) {
            return false;
         }         
      }
      return key.name.equals(name);
   }
   
   @Override
   public int hashCode() {
      int hash = types.length;
      
      hash = hash *31 + name.hashCode();
      hash = hash *31 + function.hashCode();
      
      return hash;
   }
   
   @Override
   public String toString() {
      return name;
   }
}
