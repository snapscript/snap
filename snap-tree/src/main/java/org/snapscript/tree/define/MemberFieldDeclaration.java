package org.snapscript.tree.define;

import org.snapscript.core.Evaluation;
import org.snapscript.core.define.Initializer;
import org.snapscript.tree.DeclareBlank;
import org.snapscript.tree.DeclareProperty;
import org.snapscript.tree.ModifierChecker;
import org.snapscript.tree.ModifierList;
import org.snapscript.tree.constraint.Constraint;
import org.snapscript.tree.literal.TextLiteral;

public class MemberFieldDeclaration {
   
   private final ModifierChecker checker;
   private final TextLiteral identifier;
   private final Constraint constraint;
   private final Evaluation value;

   public MemberFieldDeclaration(ModifierList modifiers, TextLiteral identifier, Constraint constraint, Evaluation value) {
      this.checker = new ModifierChecker(modifiers);
      this.constraint = constraint;
      this.identifier = identifier;
      this.value = value;
   }
   
   public Initializer declare(Initializer initializer) throws Exception {
      Evaluation evaluation = create(initializer);

      if (checker.isStatic()) {
         return new StaticFieldInitializer(evaluation);
      }
      return new InstanceFieldInitializer(evaluation);
   }
 
   private Evaluation create(Initializer initializer) throws Exception {
      if (checker.isConstant()) {
         return new DeclareBlank(identifier, constraint, value);
      }
      return new DeclareProperty(identifier, constraint, value);
   }
}
