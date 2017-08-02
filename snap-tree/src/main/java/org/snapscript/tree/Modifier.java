package org.snapscript.tree;

import org.snapscript.core.ModifierType;
import org.snapscript.parse.StringToken;

public class Modifier {
   
   private ModifierType type;
   private StringToken token;

   public Modifier(StringToken token) {
      this.token = token;
   }

   public ModifierType getType() {
      String name = token.getValue();

      if(type == null) {
         type =  ModifierType.resolveModifier(name);
      }
      return type;
   } 

}