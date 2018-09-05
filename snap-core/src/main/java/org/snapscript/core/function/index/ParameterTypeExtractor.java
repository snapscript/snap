package org.snapscript.core.function.index;

import java.util.List;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.function.Signature;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class ParameterTypeExtractor {

   private final Type[] empty;

   public ParameterTypeExtractor() {
      this.empty = new Type[]{};
   }

   public Type[] extract(FunctionPointer pointer) {
      Function function = pointer.getFunction();
      Signature signature = function.getSignature();
      List<Parameter> parameters = signature.getParameters();
      Type source = function.getSource();
      int size = parameters.size();

      if(source != null) {
         Type[] list = new Type[size];

         for (int i = 0; i < size; i++) {
            Scope scope = source.getScope();
            Parameter parameter = parameters.get(i);
            Constraint constraint = parameter.getConstraint();
            Type type = constraint.getType(scope);

            if (type != null) {
               list[i] = type;
            }
         }
         return list;
      }
      return empty;
   }
}
