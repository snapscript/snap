package org.snapscript.core.scope.instance;

import java.util.List;

import org.snapscript.core.type.Type;

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