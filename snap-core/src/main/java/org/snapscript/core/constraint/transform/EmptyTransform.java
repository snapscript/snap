package org.snapscript.core.constraint.transform;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.constraint.DeclarationConstraint;
import org.snapscript.core.type.Type;

public class EmptyTransform implements GenericTransform {
   
   private final GenericReference reference;
   private final Constraint constriant;
   
   public EmptyTransform(Type type) {
      this.constriant = new DeclarationConstraint(type);
      this.reference = new GenericReference(constriant);
   }

   @Override
   public GenericReference getReference(Constraint constraint) {
      return reference;
   }

}
