
package org.snapscript.tree.variable;

import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.tree.define.ThisScopeBinder;

public class InstanceResolver implements ValueResolver<Object> {
   
   private final ThisScopeBinder binder; 
   private final ValueResolver resolver;
   
   public InstanceResolver(String name) {
      this.resolver = new LocalResolver(name);
      this.binder = new ThisScopeBinder();
   }
   
   @Override
   public Value resolve(Scope scope, Object left) {
      Scope instance = binder.bind(scope, scope);
      
      if(instance != null) {
         return resolver.resolve(instance, left);
      }
      return null;
   }
}