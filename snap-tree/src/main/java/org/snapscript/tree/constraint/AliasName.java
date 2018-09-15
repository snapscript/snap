package org.snapscript.tree.constraint;

import static org.snapscript.core.ModifierType.ALIAS;

import org.snapscript.core.scope.Scope;
import org.snapscript.tree.literal.TextLiteral;

public class AliasName extends ClassName {

   public AliasName(TextLiteral literal, GenericList generics) {
      super(literal, generics);
   }

   @Override
   public int getModifiers(Scope scope) throws Exception{
      return ALIAS.mask;
   }
}