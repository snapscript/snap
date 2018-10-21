package org.snapscript.tree;

import static org.snapscript.core.ModifierType.*;

import org.snapscript.core.Evaluation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;
import org.snapscript.tree.literal.TextLiteral;

public class Global extends Declaration {

   public Global(TextLiteral identifier) {
      this(identifier, null, null);
   }

   public Global(TextLiteral identifier, Constraint constraint) {
      this(identifier, constraint, null);
   }

   public Global(TextLiteral identifier, Evaluation value) {
      this(identifier, null, value);
   }

   public Global(TextLiteral identifier, Constraint constraint, Evaluation value) {
      super(identifier, constraint, value);
   }

   public Value compile(Scope scope, int modifiers) throws Exception {
      return super.compile(scope, modifiers | STATIC.mask | PUBLIC.mask);
   }

   public Value declare(Scope scope, int modifiers) throws Exception {
      return super.declare(scope, modifiers | STATIC.mask | PUBLIC.mask);
   }
}
