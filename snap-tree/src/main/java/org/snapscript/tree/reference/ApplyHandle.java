package org.snapscript.tree.reference;

import org.snapscript.core.Evaluation;
import org.snapscript.core.function.Function;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.Value;
import org.snapscript.tree.NameReference;
import org.snapscript.tree.function.FunctionHandleBuilder;

public class ApplyHandle extends Evaluation {
   
   private final FunctionHandleBuilder builder;
   private final NameReference reference;
   
   public ApplyHandle(Evaluation method) {
      this.builder = new FunctionHandleBuilder();
      this.reference = new NameReference(method);
   }

   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      String match = reference.getName(scope);
      Module module = scope.getModule(); // is this the correct module?
      Function function = builder.create(module, left, match); 
      
      return Value.getTransient(function);
   }
}