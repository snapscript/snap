package org.snapscript.tree.constraint;

import static java.util.Collections.EMPTY_LIST;

import java.util.List;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.tree.NameReference;
import org.snapscript.tree.define.TypeName;
import org.snapscript.tree.literal.TextLiteral;

public class AliasName implements TypeName {
   
   private final NameReference reference;

   public AliasName(TextLiteral literal) {
      this.reference = new NameReference(literal);
   }
   
   @Override
   public int getModifiers(Scope scope) throws Exception{
      return 0;
   }
   
   @Override
   public String getName(Scope scope) throws Exception{ // called from outer class
      return reference.getName(scope);
   }
   
   @Override
   public List<Constraint> getGenerics(Scope scope) throws Exception {
      return EMPTY_LIST;
   }
}