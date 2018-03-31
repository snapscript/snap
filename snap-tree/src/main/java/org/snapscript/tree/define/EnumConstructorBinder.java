package org.snapscript.tree.define;

import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;

import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.function.find.FunctionCall;
import org.snapscript.core.function.find.FunctionFinder;
import org.snapscript.tree.ArgumentList;

public class EnumConstructorBinder {

   private final ArgumentList arguments;
   
   public EnumConstructorBinder(ArgumentList arguments) {
      this.arguments = arguments;
   }
   
   public FunctionCall bind(Scope scope, Type type) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      FunctionFinder binder = context.getFinder();
      
      if(arguments != null) {
         Object[] array = arguments.create(scope, type); // arguments have no left hand side
         return binder.bindStatic(scope, type, TYPE_CONSTRUCTOR, array);
      }
      return binder.bindStatic(scope, type, TYPE_CONSTRUCTOR, type);
   }
}