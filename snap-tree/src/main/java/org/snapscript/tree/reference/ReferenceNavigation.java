package org.snapscript.tree.reference;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.parse.StringToken;

public class ReferenceNavigation implements Evaluation {
   
   private final ReferenceOperator operator;
   private final Evaluation part;
   private final Evaluation next;
   
   public ReferenceNavigation(Evaluation part) {
      this(part, null, null);
   }
   
   public ReferenceNavigation(Evaluation part, StringToken operator, Evaluation next) {
      this.operator = ReferenceOperator.resolveOperator(operator);
      this.part = part;
      this.next = next;
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      Value value = part.evaluate(scope, left);         
 
      if(next != null) {
         return operator.operate(scope, next, value);
      }
      return value;
   }      
}