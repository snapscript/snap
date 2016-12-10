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
      
      while(base == null && parent != null) {
         String prefix = parent.getName();
         
         if(prefix != null) {
            base = module.getType(prefix + '$'+name);
         }
         parent = parent.getOuter();
      }
      return base;
   }  
}
