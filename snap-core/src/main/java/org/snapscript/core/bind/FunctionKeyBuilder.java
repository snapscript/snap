package org.snapscript.core.bind;

import org.snapscript.core.Module;
import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;

public class FunctionKeyBuilder {

   private final TypeExtractor extractor;
   private final Object[] empty;
   
   public FunctionKeyBuilder(TypeExtractor extractor) {
      this.empty = new Object[]{};
      this.extractor = extractor;
   }
   
   public Object create(Type source, String function, Object... list) throws Exception {
      String name = source.getName();
      
      if(list.length > 0) {
         Object[] types = new Object[list.length];
         
         for(int i = 0; i < list.length; i++) {
            Object value = list[i];
            
            if(value != null) {
               types[i] = extractor.getType(value);
            }
         }
         return new FunctionKey(source, name, function, types);
      }
      return new FunctionKey(source, name, function, empty);
   }
   
   public Object create(Module source, String function, Object... list) throws Exception {
      String name = source.getName();
      
      if(list.length > 0) {
         Object[] types = new Object[list.length];
         
         for(int i = 0; i < list.length; i++) {
            Object value = list[i];
            
            if(value != null) {
               types[i] = extractor.getType(value);
            }
         }
         return new FunctionKey(source, name, function, types);
      }
      return new FunctionKey(source, name, function, empty);
   }
}
