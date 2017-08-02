package org.snapscript.tree.define;

import static org.snapscript.core.ModifierType.PUBLIC;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Type;
import org.snapscript.core.TypeLoader;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationFunction;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.function.ParameterBuilder;
import org.snapscript.core.function.Signature;

public class AnyFunctionBuilder {
   
   private final AnyInvocationGenerator generator;
   private final ParameterBuilder builder;
   
   public AnyFunctionBuilder() {
      this.generator = new AnyInvocationGenerator();
      this.builder = new ParameterBuilder();
   }

   public Function create(Type type, String name, Class invoke, Class... types) throws Exception {
      Module module = type.getModule();
      Context context = module.getContext();
      TypeLoader loader = context.getLoader();
      Invocation invocation = (Invocation)generator.create(invoke);
      
      if(invocation == null) {
         throw new InternalStateException("Could not create invocation for " + invoke);
      }
      List<Parameter> parameters = new ArrayList<Parameter>();
      Signature signature = new Signature(parameters, module, null);
      
      for(int i = 0; i < types.length; i++){
         Class require = types[i];
         Type constraint = loader.loadType(require);
         Parameter parameter = null;
         
         if(require == Object.class) { // avoid proxy wrapping
            parameter = builder.create(null, i);
         } else {
            parameter = builder.create(constraint, i);
         }
         parameters.add(parameter);
      }
      return new InvocationFunction(signature, invocation, type, null, name, PUBLIC.mask);
   }
}