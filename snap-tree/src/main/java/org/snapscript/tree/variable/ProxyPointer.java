package org.snapscript.tree.variable;

import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.convert.ProxyWrapper;

public class ProxyPointer implements VariablePointer<Object> {
   
   private final VariableBinder binder;
   private final ObjectPointer pointer;
   private final String name;
   
   public ProxyPointer(VariableBinder binder, String name) {
      this.pointer = new ObjectPointer(name);
      this.binder = binder;
      this.name = name;
   }
   
   @Override
   public Value get(Scope scope, Object left) {
      Module module = scope.getModule();
      Context context = module.getContext();
      ProxyWrapper wrapper = context.getWrapper();
      Object object = wrapper.fromProxy(left);
      
      if(object != left) {
         return binder.bind(scope, object, name);
      }
      return pointer.get(scope, left);
   }
}