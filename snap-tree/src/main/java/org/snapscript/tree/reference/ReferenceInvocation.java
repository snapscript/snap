package org.snapscript.tree.reference;

import java.util.concurrent.Callable;

import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
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
      Value value = Value.getTransient(left);        
      Object[] array = arguments.create(scope); 
      Callable<Value> call = binder.bindValue(value, array);
      int width = array.length;
      
      if(call == null) {
         throw new InternalStateException("Result was not a closure of " + width +" arguments");
      }
      return call.call();
   }
}