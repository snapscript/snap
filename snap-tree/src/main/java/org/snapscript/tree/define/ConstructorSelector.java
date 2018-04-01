package org.snapscript.tree.define;

import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeBody;
import org.snapscript.core.Allocation;
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

   public Allocation define(TypeBody body, Type type, Scope scope) throws Exception {
      Type base = extractor.extractor(type);
      
      if(part != null){
         return part.define(body, type, scope);              
      }
      if(base != null) {
         return constructor.define(body, type, scope);
      }
      return new PrimitiveState(); 
   }
}