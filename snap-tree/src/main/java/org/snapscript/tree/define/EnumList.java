package org.snapscript.tree.define;

import static org.snapscript.core.type.Order.STATIC;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeBody;
import org.snapscript.core.type.TypePart;
import org.snapscript.core.type.TypeState;

public class EnumList extends TypePart {
   
   private final EnumValue[] values;
   
   public EnumList(EnumValue... values){
      this.values = values;
   }

   @Override
   public TypeState define(TypeBody body, Type type, Scope scope) throws Exception {
      TypeStateCollector collector = new TypeStateCollector(STATIC);
      int index = 0;
      
      for(EnumValue value : values) {
         TypeState state = value.define(body, type, index++);
         
         if(state != null) {
            collector.update(state);
         }
      }
      return collector;
   }
}