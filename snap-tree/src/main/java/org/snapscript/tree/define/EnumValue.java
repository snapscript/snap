package org.snapscript.tree.define;

import org.snapscript.core.Type;
import org.snapscript.core.define.Initializer;
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

   public Initializer compile(Type type, int index) throws Exception { 
      return new EnumInitializer(key, arguments, index);
   }
}