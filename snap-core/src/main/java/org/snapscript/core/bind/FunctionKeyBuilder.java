package org.snapscript.core.bind;

import org.snapscript.core.Module;
import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.TypeLoader;

public class FunctionKeyBuilder {

   private final TypeExtractor extractor;
   
   public FunctionKeyBuilder(TypeLoader loader) {
      this.extractor = new TypeExtractor(loader);
   }
   
   public Object create(Type source, String name, Object... list) throws Exception {
      Object[] types = new Object[list.length];
      
      for(int i = 0; i < list.length; i++) {
         Object value = list[i];
         
         if(value != null) {
            types[i] = extractor.extract(value);
         }
      }
      return new FunctionKey(source, name, types);
   }
   
   public Object create(Module source, String name, Object... list) throws Exception {
      Object[] types = new Object[list.length];
      
      for(int i = 0; i < list.length; i++) {
         Object value = list[i];
         
         if(value != null) {
            types[i] = extractor.extract(value);
         }
      }
      return new FunctionKey(source, name, types);
   }
}
