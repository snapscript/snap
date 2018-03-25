package org.snapscript.tree.define;

import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;
import org.snapscript.core.TypePart;
import org.snapscript.core.define.SuperExtractor;

public class ConstructorSelector {

   private final SuperConstructor constructor;
   private final SuperExtractor extractor;
   private final TypePart part;

   public ConstructorSelector(TypePart part){  
      this.constructor = new SuperConstructor();
      this.extractor = new SuperExtractor();
      this.part = part;
   } 

   public TypeFactory define(TypeFactory factory, Type type) throws Exception {
      Type base = extractor.extractor(type);
      
      if(part != null){
         return part.define(factory, type);              
      }
      if(base != null) {
         return constructor.define(factory, type);
      }
      return new PrimitiveConstructor(); 
   }
}