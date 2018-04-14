package org.snapscript.tree.annotation;

import org.snapscript.core.Evaluation;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;
import org.snapscript.tree.literal.TextLiteral;

public class AnnotationName extends Evaluation {

   private final TextLiteral literal;
   
   public AnnotationName(TextLiteral literal) {
      this.literal = literal;
   }

   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      return literal.evaluate(scope, left);
   }
   
}