package org.snapscript.tree.define;

import java.util.List;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.constraint.ConstraintVerifier;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.tree.constraint.TypeConstraint;

public class AliasHierarchy extends ClassHierarchy {

   private final ConstraintVerifier verifier;
   private final TypeConstraint actual;

   public AliasHierarchy(TypeConstraint actual) {
      this.verifier = new ConstraintVerifier();
      this.actual = actual;
   }

   @Override
   public void define(Scope scope, Type type) throws Exception {
      List<Constraint> types = type.getTypes();
      Type match = actual.getType(scope);

      if (match == null) {
         throw new InternalStateException("Invalid alias for type '" + type + "'");
      }
      types.add(actual);
   }

   @Override
   public void compile(Scope scope, Type type) throws Exception {
      List<Constraint> types = type.getTypes();

      for (Constraint base : types) {
         try {
            verifier.verify(scope, base);
         } catch (Exception e) {
            throw new InternalStateException("Invalid alias for type '" + type + "'", e);
         }
      }
   }
}

