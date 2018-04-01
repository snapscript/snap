package org.snapscript.tree.function;

import org.snapscript.core.Evaluation;
import org.snapscript.core.function.Function;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.Value;
import org.snapscript.tree.NameReference;

public class FunctionReference extends Evaluation {
   
   private final FunctionReferenceBuilder builder;
   private final NameReference reference;
   private final Evaluation variable;
   
   public FunctionReference(Evaluation variable, Evaluation method) {
      this.builder = new FunctionReferenceBuilder();
      this.reference = new NameReference(method);
      this.variable = variable;
   }
   
   @Override
   public void define(Scope scope) throws Exception {
      variable.define(scope);
   }

   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      Value value = variable.evaluate(scope, left);
      String match = reference.getName(scope);
      Module module = scope.getModule(); // is this the correct module?
      Object object = value.getValue();
      Function function = builder.create(module, object, match); 
      
      return Value.getTransient(function);
   }
}