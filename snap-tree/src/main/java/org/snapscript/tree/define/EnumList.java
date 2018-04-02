package org.snapscript.tree.define;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.Allocation;
import org.snapscript.core.type.TypeBody;
import org.snapscript.core.type.TypePart;

public class EnumList extends TypePart {
   
   private final EnumValue[] values;
   
   public EnumList(EnumValue... values){
      this.values = values;
   }

   @Override
   public Allocation define(TypeBody body, Type type, Scope scope) throws Exception {
      AllocationCollector collector = new AllocationCollector();
      int index = 0;
      
      for(EnumValue value : values) {
         Allocation initializer = value.define(body, type, index++);
         
         if(initializer != null) {
            collector.update(initializer);
         }
      }
      return collector;
   }
}