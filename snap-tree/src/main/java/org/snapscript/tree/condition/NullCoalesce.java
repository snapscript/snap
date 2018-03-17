package org.snapscript.tree.condition;

import org.snapscript.core.Bug;
import org.snapscript.core.Constraint;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;

public class NullCoalesce extends Evaluation {

   private final Evaluation substitute;
   private final Evaluation evaluation;
   
   public NullCoalesce(Evaluation evaluation, Evaluation substitute) {
      this.evaluation = evaluation;
      this.substitute = substitute;
   }

   @Override
   public void compile(Scope scope) throws Exception {
      evaluation.compile(scope);
      substitute.compile(scope);
   }   

   @Bug("eval both???")
   @Override
   public Constraint validate(Scope scope, Constraint left) throws Exception {
      return evaluation.validate(scope, left);
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      Value result = evaluation.evaluate(scope, null);
      Object value = result.getValue();
      
      if(value == null) {
         return substitute.evaluate(scope, left);
      } 
      return result;
   }
}