package org.snapscript.tree.define;

import static org.snapscript.core.constraint.Constraint.OBJECT;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.tree.NameReference;
import org.snapscript.tree.literal.TextLiteral;

public class GenericDeclaration {

   private final NameReference reference;
   private final Constraint constraint;
   
   public GenericDeclaration(TextLiteral identifier) {
      this(identifier, null);
   }
   
   public GenericDeclaration(TextLiteral identifier, Constraint constraint) {
      this.reference = new NameReference(identifier);
      this.constraint = constraint;
   }
   
   public Constraint getGeneric(Scope scope) throws Exception {
      String parameter = reference.getName(scope);
      
      if(constraint != null) {
         Type bound = constraint.getType(scope);
         
         if(bound != null) {
            return new GenericParameter(parameter, constraint);
         }
      }
      return new GenericParameter(parameter, OBJECT);
   }
}
