package org.snapscript.tree.define;

import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;

import java.util.concurrent.Callable;

import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.tree.ArgumentList;

public class EnumConstructorBinder {

   private final ArgumentList arguments;
   
   public EnumConstructorBinder(ArgumentList arguments) {
      this.arguments = arguments;
   }
   
   public Callable<Result> bind(Scope scope, Type type) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      FunctionBinder binder = context.getBinder();
      
      if(arguments != null) {
         Value array = arguments.create(scope, type); // arguments have no left hand side
         Object[] arguments = array.getValue();
         
         return binder.bind(scope, type, TYPE_CONSTRUCTOR, arguments);
      }
      return binder.bind(scope, type, TYPE_CONSTRUCTOR, type);
   }
}