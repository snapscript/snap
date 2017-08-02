package org.snapscript.tree.define;

import static org.snapscript.core.ModifierType.CONSTANT;
import static org.snapscript.core.ModifierType.STATIC;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;
import org.snapscript.tree.ModifierData;
import org.snapscript.tree.constraint.Constraint;
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
   
   public TypeFactory declare(TypeFactory factory, Type type) throws Exception {
      Scope scope = type.getScope();
      MemberFieldData data = declaration.create(scope);
      
      return assembler.assemble(data);
   }
}