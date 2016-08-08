package org.snapscript.tree.define;

import org.snapscript.core.Type;
import org.snapscript.core.define.Initializer;
import org.snapscript.tree.ArgumentList;

public class EnumValue {
   
   private final ArgumentList list;
   private final EnumKey key;
   
   public EnumValue(EnumKey key) {
      this(key, null);
   }
   
   public EnumValue(EnumKey key, ArgumentList list) {    
      this.list = list;
      this.key = key;
   }

   public Initializer compile(Type type, int index) throws Exception { 
      return new EnumInitializer(key, list, index);
   }
}