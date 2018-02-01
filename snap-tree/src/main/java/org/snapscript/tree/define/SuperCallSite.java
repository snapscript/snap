package org.snapscript.tree.define;

import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.dispatch.CallDispatcher;
import org.snapscript.tree.CallSite;
import org.snapscript.tree.NameReference;

public class SuperCallSite {

   private final CallSite site;
   private final Type type;
   
   public SuperCallSite(NameReference reference, Type type) {
      this.site = new CallSite(reference);
      this.type = type;
   }
   
   public CallDispatcher bind(Scope scope, Object left) throws Exception {
      Class base = type.getType();
      
      if(base != null) {
         return new SuperDispatcher(type); // native java object
      }
      return site.get(scope, left);
   }
}