package org.snapscript.tree.define;

import static org.snapscript.core.ModifierType.ABSTRACT;
import static org.snapscript.core.ModifierType.CLASS;

import org.snapscript.core.scope.Scope;
import org.snapscript.tree.constraint.ClassName;
import org.snapscript.tree.constraint.GenericList;
import org.snapscript.tree.literal.TextLiteral;

public class AbstractClassName extends ClassName {

   public AbstractClassName(TextLiteral literal, GenericList generics) {
      super(literal, generics);
   }
   
   @Override
   public int getModifiers(Scope scope) throws Exception{
      return ABSTRACT.mask | CLASS.mask;
   }
}
