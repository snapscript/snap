package org.snapscript.tree;

import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.dispatch.CallBinder;
import org.snapscript.core.dispatch.CallDispatcher;
import org.snapscript.core.dispatch.CallTable;

public class CallSite {

   private NameReference reference;
   private CallBinder binder;
   
   public CallSite(NameReference reference) {
      this.reference = reference;
   }
   
   public CallDispatcher get(Scope scope, Object left) throws Exception {
      if(binder == null) {
         Module module = scope.getModule();
         Context context = module.getContext();
         String name = reference.getName(scope);
         CallTable table = context.getTable();
         
         binder = table.resolve(name);
      }
      return binder.bind(scope, left);
   }
}
