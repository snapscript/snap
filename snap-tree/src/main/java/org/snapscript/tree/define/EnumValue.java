package org.snapscript.tree.define;

import org.snapscript.core.type.Allocation;
import org.snapscript.core.type.Type;
import org.snapscript.tree.ArgumentList;

public class EnumValue {
   
   private final ArgumentList arguments;
   private final EnumKey key;
   
   public EnumValue(EnumKey key) {
      this(key, null);
   }
   
   public EnumValue(EnumKey key, ArgumentList arguments) {    
      this.arguments = arguments;
      this.key = key;
   }

   public Allocation define(Type type, int index) throws Exception { 
      return new EnumState(key, arguments, index);
   }
}