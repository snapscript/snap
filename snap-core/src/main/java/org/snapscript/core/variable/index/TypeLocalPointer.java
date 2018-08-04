package org.snapscript.core.variable.index;

import org.snapscript.core.Bug;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.ScopeBinder;
import org.snapscript.core.scope.State;
import org.snapscript.core.variable.Value;
import org.snapscript.core.variable.bind.VariableFinder;

public class TypeLocalPointer implements VariablePointer {
   
   private final TypeInstancePointer pointer;
   private final ScopeBinder binder;
   private final String name;
   
   public TypeLocalPointer(VariableFinder finder, String name) {
      this.pointer = new TypeInstancePointer(finder, name);
      this.binder = new ScopeBinder();
      this.name = name;
   }
   
   @Override
   public Constraint getConstraint(Scope scope, Constraint left) {
      Scope instance = binder.bind(scope, scope);
      State state = instance.getState();
      Value value = state.get(name);
      
      if(value == null) {
         return pointer.getConstraint(instance, left);
      }
      return value.getConstraint();
   }
   
   @Bug
   @Override
   public Value getValue(Scope scope, Value left) {   
      Scope instance = binder.bind(scope, scope);
      State state = instance.getState();
      Value value = state.get(name);
      
      if(value == null) {
         return pointer.getValue(instance, Value.getConstant(instance, scope.getModule()));
      }
      return value;
   }
}