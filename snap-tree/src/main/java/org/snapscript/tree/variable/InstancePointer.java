package org.snapscript.tree.variable;

import static org.snapscript.core.constraint.Constraint.NONE;

import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.tree.define.ThisScopeBinder;

public class InstancePointer implements VariablePointer<Object> {
   
   private final VariablePointer pointer;
   private final ThisScopeBinder binder; 
   
   public InstancePointer(VariableFinder finder, String name) {
      this.pointer = new LocalPointer(finder, name);
      this.binder = new ThisScopeBinder();
   }

   @Override
   public Constraint check(Scope scope, Constraint left) {
      Scope instance = binder.bind(scope, scope);
      
      if(instance != null) {
         return pointer.check(instance, left);
      }
      return NONE;
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