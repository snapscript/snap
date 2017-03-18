
package org.snapscript.tree.define;

import org.snapscript.core.Type;
import org.snapscript.core.define.Initializer;

public class EnumList implements TypePart {
   
   private final EnumValue[] values;
   
   public EnumList(EnumValue... values){
      this.values = values;
   }
   
   @Override
   public Initializer define(Initializer initializer, Type type) throws Exception {
      return null;
   }

   @Override
   public Initializer compile(Initializer statement, Type type) throws Exception {
      InitializerCollector collector = new InitializerCollector();
      int index = 0;
      
      for(EnumValue value : values) {
         Initializer initializer = value.compile(type, index++);
         
         if(initializer != null) {
            collector.update(initializer);
         }
      }
      return collector;
   }   
}
