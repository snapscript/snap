package org.snapscript.tree;

import org.snapscript.core.Constraint;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;

public class Argument extends Evaluation{
   
   private final Evaluation evaluation;
   
   public Argument(Evaluation evaluation){
      this.evaluation = evaluation;
   }
   
   @Override
   public void compile(Scope scope) throws Exception {
      evaluation.compile(scope);
   }
   
   @Override
   public Constraint validate(Scope scope, Constraint left) throws Exception {
      return evaluation.validate(scope, left);
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      return evaluation.evaluate(scope, left);
   }
}