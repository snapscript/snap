package org.snapscript.tree.reference;

import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.function.find.FunctionCall;
import org.snapscript.core.function.find.FunctionFinder;
import org.snapscript.tree.ArgumentList;

public class ReferenceCurry extends Evaluation {
   
   private final ArgumentList arguments;
   
   public ReferenceCurry(ArgumentList arguments) {
      this.arguments = arguments;
   }
      
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception { 
      Module module = scope.getModule();
      Context context = module.getContext();
      FunctionFinder binder = context.getFinder();
      Value value = Value.getTransient(left);        
      Object[] array = arguments.create(scope); 
      FunctionCall call = binder.bindValue(value, array);
      int width = array.length;
      
      if(call == null) {
         throw new InternalStateException("Result was not a closure of " + width +" arguments");
      }
      return call.call();
   }
}