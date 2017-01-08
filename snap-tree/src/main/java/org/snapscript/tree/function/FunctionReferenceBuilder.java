package org.snapscript.tree.function;

import static org.snapscript.core.ModifierType.PUBLIC;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.Module;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationFunction;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.function.Signature;

public class FunctionReferenceBuilder {
   
   private final Parameter parameter;
   
   public FunctionReferenceBuilder() {
      this.parameter = new Parameter(null, null);
   }

   public Function create(Module module, Object value, String method) throws Exception {
      List<Parameter> parameters = new ArrayList<Parameter>();
      Signature signature = new Signature(parameters, module, true);
      Invocation invocation = new FunctionReferenceInvocation(value, method);
      
      parameters.add(parameter);
      
      return new InvocationFunction(signature, invocation, null, null, method, PUBLIC.mask);
   }
}