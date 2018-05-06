package org.snapscript.core.constraint.transform;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.constraint.TypeConstraint;
import org.snapscript.core.type.Type;

public class EmptyTransform implements GenericTransform {
   
   private final GenericHandle reference;
   private final Constraint constriant;
   
   public EmptyTransform(Type type) {
      this.constriant = new TypeConstraint(type);
      this.reference = new GenericHandle(constriant);
   }

   @Override
   public GenericHandle getHandle(Constraint constraint) {
      return reference;
   }

}
