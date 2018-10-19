package org.snapscript.tree.function;

import static org.snapscript.core.constraint.Constraint.NONE;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.function.dispatch.FunctionDispatcher.Call2;
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
      public Value evaluate(Scope scope, Value left) throws Exception {         
         Object[] array = arguments.create(scope); 
         FunctionCall call = resolver.resolveValue(left, array);
         int width = array.length;
         
         if(call == null) {
            throw new InternalStateException("Result was not a closure of " + width +" arguments");
         }
         return Value.getTransient(new Call2(call) {
            
            public Object invoke(Scope scope, Object source, Object... arguments) throws Exception{
               source = ((Value)source).getValue();
               return call.invoke(scope, source, arguments);
            }
         }.invoke(scope, left, array));
         
      }
   }
}
