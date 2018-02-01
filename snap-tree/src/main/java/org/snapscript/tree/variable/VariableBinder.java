package org.snapscript.tree.variable;

import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.convert.ProxyWrapper;
import org.snapscript.tree.NameReference;

public class VariableBinder {

   private final VariablePointerResolver resolver;
   
   public VariableBinder(NameReference reference) {
      this.resolver = new VariablePointerResolver(reference);
   }
   
   public Value bind(Scope scope, Object left, String name) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      ProxyWrapper wrapper = context.getWrapper();
      Object object = wrapper.fromProxy(left); // what about double wrapping?
      VariablePointer pointer = resolver.resolve(scope, object);
      Value value = pointer.get(scope, object);
      
      if(value == null) {
         throw new InternalStateException("Could not resolve '" + name +"' in scope");
      }
      return value;
   }
}