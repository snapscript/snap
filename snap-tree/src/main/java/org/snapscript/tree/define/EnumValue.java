
package org.snapscript.tree.define;

import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;
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

   public TypeFactory compile(Type type, int index) throws Exception { 
      return new EnumFactory(key, arguments, index);
   }
}