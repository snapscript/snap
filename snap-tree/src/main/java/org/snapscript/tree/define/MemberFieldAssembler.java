package org.snapscript.tree.define;

import org.snapscript.core.Evaluation;
import org.snapscript.core.define.Initializer;
import org.snapscript.tree.ModifierChecker;
import org.snapscript.tree.ModifierList;
import org.snapscript.tree.constraint.Constraint;
import org.snapscript.tree.literal.TextLiteral;

public class MemberFieldAssembler {
   
   private final ModifierChecker checker;
   private final Evaluation evaluation;

   public MemberFieldAssembler(ModifierList modifiers, TextLiteral identifier, Constraint constraint, Evaluation value) {
      this.checker = new ModifierChecker(modifiers);
      this.evaluation = new MemberFieldDeclaration(checker, identifier, constraint, value);
   }
   
   public Initializer assemble(Initializer initializer) throws Exception {
      if (checker.isStatic()) {
         return new StaticFieldInitializer(evaluation);
      }
      return new InstanceFieldInitializer(evaluation);
   }
}
