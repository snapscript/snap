package org.snapscript.core.bind;

import org.snapscript.core.Module;
import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;

public class FunctionKeyBuilder {

   private final TypeExtractor extractor;
   
   public FunctionKeyBuilder(TypeExtractor extractor) {
      this.extractor = extractor;
   }
   
   public Object create(Type source, String name, Object... list) throws Exception {
      Object[] types = new Object[list.length];
      
      for(int i = 0; i < list.length; i++) {
         Object value = list[i];
         
         if(value != null) {
            types[i] = extractor.getType(value);
         }
      }
      return new FunctionKey(source, name, types);
   }
   
   public Object create(Module source, String name, Object... list) throws Exception {
      Object[] types = new Object[list.length];
      
      for(int i = 0; i < list.length; i++) {
         Object value = list[i];
         
         if(value != null) {
            types[i] = extractor.getType(value);
         }
      }
      return new FunctionKey(source, name, types);
   }
}
