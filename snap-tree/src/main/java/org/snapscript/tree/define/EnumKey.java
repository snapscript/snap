package org.snapscript.tree.define;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.tree.literal.TextLiteral;

public class EnumKey extends Evaluation {
   
   protected final TextLiteral literal;
   
   public EnumKey(TextLiteral literal) {
      this.literal = literal;
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception{
      Value value = literal.evaluate(scope, left);
      String name = value.getValue();

      return Value.getTransient(name);
   }  
}