package org.snapscript.tree.function;

import static org.snapscript.core.ModifierType.PUBLIC;
import static org.snapscript.core.Reserved.DEFAULT_PARAMETER;
import static org.snapscript.core.constraint.Constraint.NONE;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.function.Function;
import org.snapscript.core.function.FunctionSignature;
import org.snapscript.core.function.FunctionType;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationFunction;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.function.Signature;
import org.snapscript.core.function.bind.FunctionMatcher;
import org.snapscript.core.module.Module;
import org.snapscript.core.type.Type;

public class FunctionHandleBuilder {
   
   private final FunctionMatcher matcher;
   private final Parameter parameter;
   private final boolean constructor;
   
   public FunctionHandleBuilder(FunctionMatcher matcher, boolean constructor) {
      this.parameter = new Parameter(DEFAULT_PARAMETER, NONE, false, true);
      this.constructor = constructor;
      this.matcher = matcher;
   }
   
   public Function create(Module module, Object value, String method) throws Exception {
      List<Parameter> parameters = new ArrayList<Parameter>();
      Signature signature = new FunctionSignature(parameters, module, null, true, true);
      Invocation invocation = new FunctionHandleInvocation(matcher, module, value, constructor);
      Type type = new FunctionType(signature, module, null);
      
      parameters.add(parameter);
      
      return new InvocationFunction(signature, invocation, type, null, method, PUBLIC.mask);
   }
}