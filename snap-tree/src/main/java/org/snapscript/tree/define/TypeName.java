package org.snapscript.tree.define;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.tree.NameExtractor;
import org.snapscript.tree.literal.TextLiteral;

public class TypeName {
   
   protected final NameExtractor extractor;
   
   public TypeName(TextLiteral literal) {
      this.extractor = new NameExtractor(literal);
   }
   
   public String getName(Scope scope) throws Exception{ // called from outer class
      String name = extractor.extract(scope);
      Type parent = scope.getType();
      
      if(parent != null) {
         String prefix = parent.getName();
         
         if(prefix != null) {
            return prefix + '$'+name;
         }
      }
      return name;
   }
   
   public Type getType(Scope scope) throws Exception{ // called from inner class
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
      if(base == null) {
         throw new InternalStateException("Type '" + name + "' could not be resolved");
      }
      return base;
   }  
}
