package org.snapscript.tree.define;

import static org.snapscript.core.ModifierType.CONSTANT;

import org.snapscript.core.Evaluation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.tree.NameReference;
import org.snapscript.tree.constraint.DeclarationConstraint;
import org.snapscript.tree.literal.TextLiteral;

public class MemberFieldDeclaration {

   private final DeclarationConstraint constraint;
   private final NameReference identifier;
   private final Evaluation value;
   
   public MemberFieldDeclaration(TextLiteral identifier) {
      this(identifier, null, null);
   }
   
   public MemberFieldDeclaration(TextLiteral identifier, Constraint constraint) {      
      this(identifier, constraint, null);
   }
   
   public MemberFieldDeclaration(TextLiteral identifier, Evaluation value) {
      this(identifier, null, value);
   }
   
   public MemberFieldDeclaration(TextLiteral identifier, Constraint constraint, Evaluation value) {
      this.constraint = new DeclarationConstraint(constraint);
      this.identifier = new NameReference(identifier);
      this.value = value;
   }   
   
   public MemberFieldData create(Scope scope, int modifiers) throws Exception {
      String name = identifier.getName(scope);
      Constraint require = constraint.getConstraint(scope, modifiers);   
      
      if(value == null) {
         int mask = modifiers & ~CONSTANT.mask;
         Constraint blank = constraint.getConstraint(scope, mask); // const that is not assigned
         
         return new MemberFieldData(name, blank, null);
      }
      return new MemberFieldData(name, require, value);
   }
}