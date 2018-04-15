package org.snapscript.tree.define;

import static org.snapscript.core.ModifierType.CONSTANT;
import static org.snapscript.core.ModifierType.STATIC;

import org.snapscript.core.Evaluation;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.TypeState;
import org.snapscript.core.type.Type;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.type.TypeBody;
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
   
   public TypeState declare(TypeBody body, Type type) throws Exception {
      int mask = modifiers.getModifiers();
      Scope scope = type.getScope();
      MemberFieldData data = declaration.create(scope, mask);
      
      return assembler.assemble(data);
   }
}