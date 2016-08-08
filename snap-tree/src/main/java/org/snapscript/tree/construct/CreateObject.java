package org.snapscript.tree.construct;

import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;

import java.util.concurrent.Callable;

import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.tree.ArgumentList;

public class CreateObject implements Evaluation {
   
   private final Evaluation reference;
   private final ArgumentList list;

   public CreateObject(Evaluation reference, ArgumentList list) {
      this.reference = reference;
      this.list = list;
   }      
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception { // this is rubbish
      Value value = reference.evaluate(scope, null);
      Type type = value.getValue();
      Callable<Result> call = bind(scope, type);
           
      if(call == null){
         Module module = type.getModule();
         String resource = module.getName();
         String name = type.getName();
         
         throw new InternalStateException("No constructor for '" + resource + "." + name + "'");
      }
      Result result = call.call();
      Object instance = result.getValue();
      
      return ValueType.getTransient(instance);
   }
   
   private Callable<Result> bind(Scope scope, Type type) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      FunctionBinder binder = context.getBinder();
      Class real = type.getType();
      
      if(list != null) {
         Value array = list.evaluate(scope, null); // arguments have no left hand side
         Object[] arguments = array.getValue();

         if(real == null) {
            Object[] expand = new Object[arguments.length + 1];
            
            for(int i = 0; i < arguments.length; i++) {
               expand[i + 1] = arguments[i];
            }
            expand[0] = type;
            
            return binder.bind(scope, type, TYPE_CONSTRUCTOR, expand);
         }
         return binder.bind(scope, type, TYPE_CONSTRUCTOR, arguments);
      }
      if(real == null) {
         return binder.bind(scope, type, TYPE_CONSTRUCTOR, type);
      }
      return binder.bind(scope, type, TYPE_CONSTRUCTOR);
   }
}
