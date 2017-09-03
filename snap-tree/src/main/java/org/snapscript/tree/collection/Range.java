package org.snapscript.tree.collection;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;

public class Range extends Evaluation {

   private final Evaluation start;
   private final Evaluation finish;
   
   public Range(Evaluation start, Evaluation finish) {
      this.start = start;
      this.finish = finish;
   }

   @Override
   public Value compile(Scope scope, Object left) throws Exception {
      start.compile(scope, left); // compile for stack reference
      finish.compile(scope, left);
      
      return ValueType.getTransient(null);
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      Iterable<Number> range = create(scope, left);
      return ValueType.getTransient(range);
   }
   
   private Sequence create(Scope scope, Object left) throws Exception {
      Value first = start.evaluate(scope, left);
      Value last = finish.evaluate(scope, left);
      Long firstNumber = first.getLong();
      Long lastNumber = last.getLong();
      
      return new Sequence(firstNumber, lastNumber);
   }
}