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
   public void define(Scope scope) throws Exception {
      evaluation.define(scope);
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      return evaluation.compile(scope, left);
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      return evaluation.evaluate(scope, left);
   }
}