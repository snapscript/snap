package org.snapscript.tree.define;

import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;

import org.snapscript.core.Context;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.function.search.FunctionCall;
import org.snapscript.core.function.search.FunctionSearcher;
import org.snapscript.tree.ArgumentList;

public class EnumConstructorBinder {

   private final ArgumentList arguments;
   
   public EnumConstructorBinder(ArgumentList arguments) {
      this.arguments = arguments;
   }
   
   public FunctionCall bind(Scope scope, Type type) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      FunctionSearcher binder = context.getSearcher();
      
      if(arguments != null) {
         Object[] array = arguments.create(scope, type); // arguments have no left hand side
         return binder.searchStatic(scope, type, TYPE_CONSTRUCTOR, array);
      }
      return binder.searchStatic(scope, type, TYPE_CONSTRUCTOR, type);
   }
}