
package org.snapscript.core.bind;

import org.snapscript.core.Bug;
import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;

public class FunctionKeyBuilder {

   private final TypeExtractor extractor;
   
   public FunctionKeyBuilder(TypeExtractor extractor) {
      this.extractor = extractor;
   }

   @Bug("why would the list be null here??? - list != null && list.length > 0")
   public Object create(String function, Object... list) throws Exception {
      if(list != null && list.length > 0) {
         Type[] types = new Type[list.length];
         
         for(int i = 0; i < list.length; i++) {
            Object value = list[i];
            
            if(value != null) {
               types[i] = extractor.getType(value);
            }
         }
         return new FunctionKey(function, types);
      }
      return function;
   }
}
