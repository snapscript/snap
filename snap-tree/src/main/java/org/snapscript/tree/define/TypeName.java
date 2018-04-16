package org.snapscript.tree.define;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.tree.NameReference;
import org.snapscript.tree.literal.TextLiteral;
import org.snapscript.tree.reference.ConstraintList;

public class TypeName {
   
   private final NameReference reference;
   private final ConstraintList generics;
   
   public TypeName(TextLiteral literal) {
      this(literal, null);
   }
   
   public TypeName(TextLiteral literal, ConstraintList generics) {
      this.reference = new NameReference(literal);
      this.generics = generics;
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