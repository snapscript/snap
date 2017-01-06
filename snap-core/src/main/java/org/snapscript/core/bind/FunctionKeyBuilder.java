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
      int order = source.getOrder();
      
      if(list.length > 0) {
         Object[] types = new Object[list.length];
         
         for(int i = 0; i < list.length; i++) {
            Object value = list[i];
            
            if(value != null) {
               types[i] = extractor.getType(value);
            }
         }
         return new FunctionKey(source, function, types, order);
      }
      return new FunctionKey(source, function, empty, order);
   }
   
   public Object create(Module source, String function, Object... list) throws Exception {
      int order = source.getOrder();
      
      if(list.length > 0) {
         Object[] types = new Object[list.length];
         
         for(int i = 0; i < list.length; i++) {
            Object value = list[i];
            
            if(value != null) {
               types[i] = extractor.getType(value);
            }
         }
         return new FunctionKey(source, function, types, order);
      }
      return new FunctionKey(source, function, empty, order);
   }
}
