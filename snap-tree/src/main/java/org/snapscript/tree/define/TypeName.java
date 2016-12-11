package org.snapscript.tree.define;

import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.tree.NameExtractor;
import org.snapscript.tree.literal.TextLiteral;
import org.snapscript.tree.reference.TypeReference;

public class TypeName {
   
   protected final TypeReference reference;
   protected final NameExtractor extractor;
   
   public TypeName(TextLiteral literal) {
      this.reference = new TypeReference(literal);
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
}
