package org.snapscript.tree.define;

import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;
import org.snapscript.core.TypePart;

public class EnumList extends TypePart {
   
   private final EnumValue[] values;
   
   public EnumList(EnumValue... values){
      this.values = values;
   }

   @Override
   public TypeFactory define(TypeFactory factory, Type type, Scope scope) throws Exception {
      TypeFactoryCollector collector = new TypeFactoryCollector();
      int index = 0;
      
      for(EnumValue value : values) {
         TypeFactory initializer = value.define(type, index++);
         
         if(initializer != null) {
            collector.update(initializer);
         }
      }
      return collector;
   }
}