package org.snapscript.core.bind;

public class FunctionKey {      

   private final Object[] types;
   private final String function;
   private final Object source;
   private final int order;
   
   public FunctionKey(Object source, String function, Object[] types, int order) {
      this.function = function;
      this.source = source;
      this.types = types;
      this.order = order;
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
      return key.function.equals(function);
   }
   
   @Override
   public int hashCode() {
      int hash = function.hashCode();
      
      hash = hash *31 + order;
      hash = hash *31 + types.length;
      
      return hash;
   }
}
