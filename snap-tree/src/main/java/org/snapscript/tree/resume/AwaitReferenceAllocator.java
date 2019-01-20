package org.snapscript.tree.resume;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.index.Local;
import org.snapscript.core.variable.Value;
import org.snapscript.tree.DeclarationAllocator;

public class AwaitReferenceAllocator extends DeclarationAllocator {

   public AwaitReferenceAllocator(Constraint constraint) {
      super(constraint, null);
   }

   protected <T extends Value> T declare(Scope scope, String name, Constraint type, int modifiers) throws Exception {
      return (T)Local.getReference(null, name, type);
   }

   protected <T extends Value> T assign(Scope scope, String name, Object value, Constraint type, int modifiers) throws Exception {
      return (T)Local.getReference(value, name, type);
   }
}
