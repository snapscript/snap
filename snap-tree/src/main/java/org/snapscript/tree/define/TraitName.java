package org.snapscript.tree.define;

import org.snapscript.tree.literal.TextLiteral;
import org.snapscript.tree.reference.ConstraintList;

public class TraitName extends TypeName {
   
   public TraitName(TextLiteral literal) {
      super(literal);
   } 
   
   public TraitName(TextLiteral literal, ConstraintList generics) {
      super(literal, generics);
   } 
}