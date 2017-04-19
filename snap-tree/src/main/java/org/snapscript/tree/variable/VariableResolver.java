
package org.snapscript.tree.variable;

import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.convert.ProxyWrapper;

public class VariableResolver {

   private final VariableBinder binder;
   
   public VariableResolver() {
      this.binder = new VariableBinder();
   }
   
   public Value resolve(Scope scope, Object left, String name) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      ProxyWrapper wrapper = context.getWrapper();
      Object object = wrapper.fromProxy(left);
      ValueResolver resolver = binder.bind(scope, object, name);
      Value value = resolver.resolve(scope, object);
      
      if(value == null) {
         throw new InternalStateException("Could not resolve '" + name +"' in scope");
      }
      return value;
   }
}
