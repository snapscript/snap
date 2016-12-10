package org.snapscript.tree.define;

import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.tree.literal.TextLiteral;

public class TraitName extends TypeName {
   
   public TraitName(TextLiteral literal) {
      super(literal);
   }
   
   @Override
   public Type getType(Scope scope) throws Exception{
      String name = extractor.extract(scope);
      Module module = scope.getModule();
      Type base = module.getType(name);
      Type parent = scope.getType();
      
      if(base == null && parent != null) {
         Type outer = parent.getOuter();
         
         if(outer != null) {
            String prefix = outer.getName();
            
            if(prefix != null) {
               return module.getType(prefix + '$'+name);
            }
         }
      }
      return base;
   }  
}
