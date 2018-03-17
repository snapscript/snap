package org.snapscript.tree.define;

import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;

public class EnumList implements TypePart {
   
   private final EnumValue[] values;
   
   public EnumList(EnumValue... values){
      this.values = values;
   }
   
   @Override
   public TypeFactory create(TypeFactory factory, Type type) throws Exception {
      return null;
   }
   
   @Override
   public TypeFactory compile(TypeFactory factory, Type type) throws Exception {
      return null;
   }

   @Override
   public TypeFactory define(TypeFactory factory, Type type) throws Exception {
      TypeFactoryCollector collector = new TypeFactoryCollector();
      int index = 0;
      
      for(EnumValue value : values) {
         TypeFactory initializer = value.compile(type, index++);
         
         if(initializer != null) {
            collector.update(initializer);
         }
      }
      return collector;
   }   
}