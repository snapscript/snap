package org.snapscript.tree.collection;

import static org.snapscript.core.constraint.Constraint.ITERABLE;

import org.snapscript.core.Evaluation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Data;
import org.snapscript.core.variable.Value;

public class Range extends Evaluation {

   private final Evaluation start;
   private final Evaluation finish;
   
   public Range(Evaluation start, Evaluation finish) {
      this.start = start;
      this.finish = finish;
   }

   @Override
   public void define(Scope scope) throws Exception {
      start.define(scope); // compile for stack reference
      finish.define(scope);
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      return ITERABLE;
   }
   
   @Override
   public Value evaluate(Scope scope, Value left) throws Exception {
      Iterable<Data> range = create(scope, left);
      Module module = scope.getModule();
      
      return Value.getTransient(range, module);
   }
   
   private Sequence create(Scope scope, Value left) throws Exception {
      Value first = start.evaluate(scope, left);
      Value last = finish.evaluate(scope, left);
      Module module = scope.getModule();
      long start = first.getData().getLong();
      long finish = last.getData().getLong();
      
      return new Sequence(module, start, finish);
   }
}