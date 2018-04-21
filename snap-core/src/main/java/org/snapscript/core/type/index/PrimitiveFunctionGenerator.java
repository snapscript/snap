package org.snapscript.core.type.index;

import static org.snapscript.core.ModifierType.PUBLIC;
import static org.snapscript.core.ModifierType.STATIC;
import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;
import static org.snapscript.core.constraint.Constraint.NONE;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.FunctionSignature;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationFunction;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.function.ParameterBuilder;
import org.snapscript.core.function.Signature;
import org.snapscript.core.module.Module;
import org.snapscript.core.type.Type;

public class PrimitiveFunctionGenerator {
   
   private final GenericConstraintExtractor extractor;
   private final PrimitiveFunctionAccessor accessor;
   private final ParameterBuilder builder;
   
   public PrimitiveFunctionGenerator(TypeIndexer indexer) {
      this.extractor = new GenericConstraintExtractor(indexer);
      this.accessor = new PrimitiveFunctionAccessor();
      this.builder = new ParameterBuilder();
   }

   public Function generate(Type type, Constraint returns, String name, Class invoke, Class... types) {
      Module module = type.getModule();
      Invocation invocation = accessor.create(invoke);
      
      if(invocation == null) {
         throw new InternalStateException("Could not create invocation for " + invoke);
      }
      List<Parameter> parameters = new ArrayList<Parameter>();
      Signature signature = new FunctionSignature(parameters, module, null, true);
      
      for(int i = 0; i < types.length; i++){
         Class require = types[i];
         Constraint constraint = extractor.extractType(require, null);
         Parameter parameter = null;
         
         if(require == Object.class) { // avoid proxy wrapping
            parameter = builder.create(NONE, i);
         } else {
            parameter = builder.create(constraint, i);
         }
         parameters.add(parameter);
      }            
      if(name.equals(TYPE_CONSTRUCTOR)) {
         return new InvocationFunction(signature, invocation, type, returns, name, STATIC.mask | PUBLIC.mask);
      }
      return new InvocationFunction(signature, invocation, type, returns, name, PUBLIC.mask);
   }
}