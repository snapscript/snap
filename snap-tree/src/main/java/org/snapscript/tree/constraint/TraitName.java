package org.snapscript.tree.constraint;

import static org.snapscript.core.ModifierType.ABSTRACT;
import static org.snapscript.core.ModifierType.TRAIT;

import org.snapscript.core.scope.Scope;
import org.snapscript.tree.literal.TextLiteral;

public class TraitName extends ClassName {

   public TraitName(TextLiteral literal, GenericList generics) {
      super(literal, generics);
   } 
   
   @Override
   public int getModifiers(Scope scope) throws Exception{
      return TRAIT.mask | ABSTRACT.mask;
   }
}