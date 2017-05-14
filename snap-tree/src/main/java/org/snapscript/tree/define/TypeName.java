
package org.snapscript.tree.define;

import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.tree.NameReference;
import org.snapscript.tree.literal.TextLiteral;

public class TypeName {
   
   private final NameReference reference;
   
   public TypeName(TextLiteral literal) {
      this.reference = new NameReference(literal);
   }
   
   public String getName(Scope scope) throws Exception{ // called from outer class
      String name = reference.getName(scope);
      Type parent = scope.getType();
      
      if(parent != null) {
         String prefix = parent.getName();
         
         if(prefix != null) {
            return prefix + '$'+name;
         }
      }
      return name;
   }
}
