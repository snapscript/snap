package org.snapscript.tree;

import org.snapscript.core.ModifierType;
import org.snapscript.parse.StringToken;

public class Modifier {
   
   private final StringToken token;
   
   public Modifier(StringToken token) {
      this.token = token;
   }

   public ModifierType getType() {
      String name = token.getValue();

      if(name != null) {
         return ModifierType.resolveModifier(name);
      }
      return null;
   }

}
