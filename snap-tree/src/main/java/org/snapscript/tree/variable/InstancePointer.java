package org.snapscript.tree.variable;

import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.tree.define.ThisScopeBinder;

public class InstancePointer implements VariablePointer<Object> {
   
   private final VariablePointer pointer;
   private final ThisScopeBinder binder; 
   
   public InstancePointer(ConstantResolver resolver, String name) {
      this.pointer = new LocalPointer(resolver, name);
      this.binder = new ThisScopeBinder();
   }
   
   @Override
   public Value get(Scope scope, Object left) {
      Scope instance = binder.bind(scope, scope);
      
      if(instance != null) {
         return pointer.get(instance, left);
      }
      return null;
   }
}