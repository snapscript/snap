package org.snapscript.tree.reference;

import java.util.concurrent.Callable;

import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.tree.ArgumentList;

public class ReferenceInvocation extends Evaluation {
   
   private final ArgumentList arguments;
   
   public ReferenceInvocation(ArgumentList arguments) {
      this.arguments = arguments;
   }
      
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception { 
      Module module = scope.getModule();
      Context context = module.getContext();
      FunctionBinder binder = context.getBinder();
      Value value = ValueType.getTransient(left);        
      Value array = arguments.create(scope); 
      Object[] arguments = array.getValue();
      Callable<Result> call = binder.bind(value, arguments);
      int width = arguments.length;
      
      if(call == null) {
         throw new InternalStateException("Result was not a closure of " + width +" arguments");
      }
      Result result = call.call();
      Object object = result.getValue();
      
      return ValueType.getTransient(object);
   }
}