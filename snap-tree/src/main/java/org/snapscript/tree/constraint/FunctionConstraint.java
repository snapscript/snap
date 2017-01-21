package org.snapscript.tree.constraint;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.core.function.Signature;
import org.snapscript.tree.function.ParameterList;

public class FunctionConstraint implements Evaluation {

   private final ParameterList list;
   
   public FunctionConstraint(ParameterList list) {
      this.list = list;
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      Signature signature = list.create(scope);
      Type type = signature.getDefinition();
      
      return ValueType.getTransient(type);
   }
}
