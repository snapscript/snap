
package org.snapscript.tree.function;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.core.function.Function;
import org.snapscript.tree.NameReference;

public class FunctionReference implements Evaluation {
   
   private final FunctionReferenceBuilder builder;
   private final NameReference referene;
   private final Evaluation variable;
   
   public FunctionReference(Evaluation variable, Evaluation method) {
      this.builder = new FunctionReferenceBuilder();
      this.referene = new NameReference(method);
      this.variable = variable;
   }

   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      Value value = variable.evaluate(scope, left);
      String match = referene.getName(scope);
      Module module = scope.getModule(); // is this the correct module?
      Object object = value.getValue();
      Function function = builder.create(module, object, match); 
      
      return ValueType.getTransient(function);
   }
}
