package org.snapscript.tree.define;

import java.util.concurrent.atomic.AtomicBoolean;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;

public class StaticField extends StaticBlock {
   
   private final AtomicBoolean compile;
   private final Evaluation evaluation;
   
   public StaticField(Evaluation evaluation){
      this.compile = new AtomicBoolean(false);
      this.evaluation = evaluation;
   }
   
   @Override
   public void compile(Scope scope, Type type) throws Exception {
      if(compile.compareAndSet(false, true)) {
         evaluation.compile(scope, null);
      }
   }

   @Override
   protected void allocate(Type type) throws Exception {
      Scope scope = type.getScope();
      evaluation.evaluate(scope, null);
   }
}