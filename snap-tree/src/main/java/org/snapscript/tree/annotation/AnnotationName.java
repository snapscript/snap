package org.snapscript.tree.annotation;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.tree.literal.TextLiteral;

public class AnnotationName implements Evaluation {

   private final TextLiteral literal;
   
   public AnnotationName(TextLiteral literal) {
      this.literal = literal;
   }

   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      return literal.evaluate(scope, left);
   }
   
}
