package org.snapscript.core.module;

import org.snapscript.core.Context;
import org.snapscript.core.function.Function;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.stack.ThreadStack;
import org.snapscript.core.type.Type;

public class ModuleScopeBinder {

   public ModuleScopeBinder() {
      super();
   }

   public Scope bind(Scope scope) {
      Module module = scope.getModule();
      Context context = module.getContext();
      ThreadStack stack = context.getStack();
      Function function = stack.current(); // we can determine the function type
      
      if(function != null) {
         Type type = function.getType();
         
         if(type != null) {
            Scope current = type.getScope();
            
            if(current != null) {
               return current;
            }
         }
      }
      return scope;
   }  
}