package org.snapscript.tree.condition;

import org.snapscript.core.Bug;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.constraint.Constraint;

public class NullCoalesce extends Evaluation {

   private final Evaluation substitute;
   private final Evaluation evaluation;
   
   public NullCoalesce(Evaluation evaluation, Evaluation substitute) {
      this.evaluation = evaluation;
      this.substitute = substitute;
   }

   @Override
   public void define(Scope scope) throws Exception {
      evaluation.define(scope);
      substitute.define(scope);
   }   

   @Bug("eval both???")
   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      return evaluation.compile(scope, left);
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