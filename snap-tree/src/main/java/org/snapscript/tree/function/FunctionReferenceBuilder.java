package org.snapscript.tree.function;

import static org.snapscript.core.ModifierType.PUBLIC;
import static org.snapscript.core.Reserved.DEFAULT_PARAMETER;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.Module;
import org.snapscript.core.Type;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.FunctionSignature;
import org.snapscript.core.function.FunctionType;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationFunction;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.function.Signature;

public class FunctionReferenceBuilder {
   
   private final Parameter parameter;
   
   public FunctionReferenceBuilder() {
      this.parameter = new Parameter(DEFAULT_PARAMETER, null, false, true);
   }
   
   public Function create(Module module, Object value, String method) throws Exception {
      List<Parameter> parameters = new ArrayList<Parameter>();
      Signature signature = new FunctionSignature(parameters, module, null, true, true);
      Invocation invocation = new FunctionReferenceInvocation(module, value, method);
      Type type = new FunctionType(signature, module, null);
      
      parameters.add(parameter);
      
      return new InvocationFunction(signature, invocation, type, null, method, PUBLIC.mask);
   }
}