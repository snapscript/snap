package org.snapscript.tree.define;

import static org.snapscript.core.ModifierType.CONSTANT;
import static org.snapscript.core.ModifierType.STATIC;

import org.snapscript.core.Evaluation;
import org.snapscript.core.define.Initializer;
import org.snapscript.tree.ModifierData;
import org.snapscript.tree.constraint.Constraint;
import org.snapscript.tree.literal.TextLiteral;

public class TraitConstantDeclaration {
   
   private final TextLiteral identifier;
   private final Constraint constraint;
   private final Evaluation value;

   public TraitConstantDeclaration(TextLiteral identifier, Constraint constraint, Evaluation value) {
      this.constraint = constraint;
      this.identifier = identifier;
      this.value = value;
   }
   
   public Initializer declare(Initializer initializer) throws Exception {
      ModifierData modifiers = new ModifierData(CONSTANT, STATIC);
      Evaluation evaluation = new MemberFieldDeclaration(modifiers, identifier, constraint, value);
      
      return new StaticFieldInitializer(evaluation);
   }
}
