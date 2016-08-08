package org.snapscript.core.define;

import java.util.List;

import org.snapscript.core.Type;

public class SuperExtractor {
   
   public SuperExtractor() {
      super();
   }
   
   public Type extractor(Type type) {
      List<Type> types = type.getTypes();
      
      for(Type base : types) {
         return base;
      }
      return null;
   }

}
