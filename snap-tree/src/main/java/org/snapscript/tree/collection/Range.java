package org.snapscript.tree.collection;

import org.snapscript.core.Constraint;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;

public class Range extends Evaluation {

   private final Evaluation start;
   private final Evaluation finish;
   
   public Range(Evaluation start, Evaluation finish) {
      this.start = start;
      this.finish = finish;
   }

   @Override
   public void compile(Scope scope) throws Exception {
      start.compile(scope); // compile for stack reference
      finish.compile(scope);
   }
   
   @Override
   public Constraint validate(Scope scope, Constraint left) throws Exception {
      return Constraint.getInstance(scope, Iterable.class);
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      Iterable<Number> range = create(scope, left);
      return Value.getTransient(range);
   }
   
   private Sequence create(Scope scope, Object left) throws Exception {
      Value first = start.evaluate(scope, left);
      Value last = finish.evaluate(scope, left);
      long start = first.getLong();
      long finish = last.getLong();
      
      return new Sequence(start, finish);
   }
}