package org.snapscript.tree.define;

import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;
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

   public TypeFactory compile(TypeFactory factory, Type type) throws Exception {
      Type base = extractor.extractor(type);
      
      if(part != null){
         return part.compile(factory, type);              
      }
      if(base != null) {
         return constructor.compile(factory, type);
      }
      return new PrimitiveConstructor(); 
   }
}