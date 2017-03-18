
package org.snapscript.tree.define;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.tree.NameExtractor;
import org.snapscript.tree.constraint.Constraint;
import org.snapscript.tree.constraint.ConstraintExtractor;
import org.snapscript.tree.literal.TextLiteral;

public class MemberFieldDeclaration {

   private final ConstraintExtractor constraint;
   private final NameExtractor identifier;
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
      this.identifier = new NameExtractor(identifier);
      this.constraint = new ConstraintExtractor(constraint);
      this.value = value;
   }   

   public MemberFieldData create(Scope scope) throws Exception {
      Type type = constraint.extract(scope);
      String name = identifier.extract(scope);
  
      return new MemberFieldData(name, type, value);
   }
}
