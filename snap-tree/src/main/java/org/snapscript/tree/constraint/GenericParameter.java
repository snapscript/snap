package org.snapscript.tree.constraint;

import static org.snapscript.core.constraint.Constraint.OBJECT;

import java.util.List;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.constraint.TypeParameterConstraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.tree.NameReference;
import org.snapscript.tree.literal.TextLiteral;

public class GenericParameter {

   private final NameReference reference;
   private final Constraint constraint;
   
   public GenericParameter(TextLiteral identifier) {
      this(identifier, OBJECT);
   }
   
   public GenericParameter(TextLiteral identifier, Constraint constraint) {
      this.reference = new NameReference(identifier);
      this.constraint = constraint;
   }
   
   public Constraint getGeneric(Scope scope) throws Exception {
      String parameter = reference.getName(scope);
      Type type = constraint.getType(scope);
      List<Constraint> generics = constraint.getGenerics(scope);
         
      return new TypeParameterConstraint(type, generics, parameter);
   }
}
