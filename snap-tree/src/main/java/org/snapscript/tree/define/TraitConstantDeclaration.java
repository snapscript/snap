package org.snapscript.tree.define;

import static org.snapscript.core.ModifierType.CONSTANT;
import static org.snapscript.core.ModifierType.STATIC;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeBody;
import org.snapscript.core.Allocation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.tree.ModifierData;
import org.snapscript.tree.literal.TextLiteral;

public class TraitConstantDeclaration {
   
   private final MemberFieldDeclaration declaration;
   private final MemberFieldAssembler assembler;
   private final ModifierData modifiers;

   public TraitConstantDeclaration(TextLiteral identifier, Constraint constraint, Evaluation value) {
      this.declaration = new MemberFieldDeclaration(identifier, constraint, value);
      this.modifiers = new ModifierData(CONSTANT, STATIC);
      this.assembler = new MemberFieldAssembler(modifiers);
   }
   
   public Allocation declare(TypeBody body, Type type) throws Exception {
      int mask = modifiers.getModifiers();
      Scope scope = type.getScope();
      MemberFieldData data = declaration.create(scope, mask);
      
      return assembler.assemble(data);
   }
}