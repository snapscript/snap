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

   private final ArgumentList list;
   
   public EnumConstructorBinder(ArgumentList list) {
      this.list = list;
   }
   
   public Callable<Result> bind(Scope scope, Type type) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      FunctionBinder binder = context.getBinder();
      
      if(list != null) {
         Value array = list.evaluate(scope, null); // arguments have no left hand side
         Object[] arguments = array.getValue();
         
         if(arguments.length > 0) {
            Object[] expand = new Object[arguments.length + 1];
            
            for(int i = 0; i < arguments.length; i++) {
               expand[i + 1] = arguments[i];
            }
            expand[0] = type;
            
            return binder.bind(scope, type, TYPE_CONSTRUCTOR, expand);
         }
      }
      return binder.bind(scope, type, TYPE_CONSTRUCTOR, type);
   }
}
