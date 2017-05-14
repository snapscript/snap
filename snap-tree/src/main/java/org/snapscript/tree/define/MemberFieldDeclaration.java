
package org.snapscript.tree.define;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.tree.NameReference;
import org.snapscript.tree.constraint.Constraint;
import org.snapscript.tree.constraint.ConstraintReference;
import org.snapscript.tree.literal.TextLiteral;

public class MemberFieldDeclaration {

   private final ConstraintReference constraint;
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
      this.identifier = new NameReference(identifier);
      this.constraint = new ConstraintReference(constraint);
      this.value = value;
   }   

   public MemberFieldData create(Scope scope) throws Exception {
      Type type = constraint.getConstraint(scope);
      String name = identifier.getName(scope);
  
      return new MemberFieldData(name, type, value);
   }
}
