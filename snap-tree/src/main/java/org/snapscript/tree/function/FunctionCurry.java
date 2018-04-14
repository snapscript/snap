package org.snapscript.tree.function;

import static org.snapscript.core.constraint.Constraint.NONE;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.resolve.FunctionCall;
import org.snapscript.core.function.resolve.FunctionResolver;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;
import org.snapscript.tree.ArgumentList;

public class FunctionCurry implements Compilation {
   
   private final ArgumentList arguments;
   
   public FunctionCurry(ArgumentList arguments) {
      this.arguments = arguments;
   }
   
   @Override
   public Object compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      FunctionResolver resolver = context.getResolver();
      
      return new CompileResult(resolver, arguments);
   }

   private static class CompileResult extends Evaluation {
   
      private final FunctionResolver resolver;
      private final ArgumentList arguments;
      
      public CompileResult(FunctionResolver resolver, ArgumentList arguments) {
         this.arguments = arguments;
         this.resolver = resolver;
      }
      
      @Override
      public Constraint compile(Scope scope, Constraint left) throws Exception {         
         arguments.compile(scope);
         return NONE;
      }
         
      @Override
      public Value evaluate(Scope scope, Object left) throws Exception { 
         Value value = Value.getTransient(left);        
         Object[] array = arguments.create(scope); 
         FunctionCall call = resolver.resolveValue(value, array);
         int width = array.length;
         
         if(call == null) {
            throw new InternalStateException("Result was not a closure of " + width +" arguments");
         }
         return call.call();
      }
   }
}
