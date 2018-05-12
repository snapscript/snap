package org.snapscript.core.variable.index;

import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.property.PropertyValue;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.ScopeBinder;
import org.snapscript.core.scope.State;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;
import org.snapscript.core.variable.bind.VariableFinder;
import org.snapscript.core.variable.bind.VariableResult;

public class TypeLocalPointer implements VariablePointer<Scope> {
   
   private final AtomicReference<VariableResult> reference;
   private final ScopeBinder binder;
   private final VariableFinder finder;
   private final String name;
   
   public TypeLocalPointer(VariableFinder finder, String name) {
      this.reference = new AtomicReference<VariableResult>();
      this.binder = new ScopeBinder();
      this.finder = finder;
      this.name = name;
   }
   
   @Override
   public Constraint getConstraint(Scope scope, Constraint left) {
      Scope instance = binder.bind(scope, scope);
      State state = instance.getState();
      Value value = state.get(name);
      
      if(value == null) {
         Type type = instance.getType();
         VariableResult property = finder.findAll(scope, type, name);
         
         if(property != null) {
            reference.set(property);
            return property.getConstraint(left);
         }
         return null;
      }
      return value;
   }
   
   @Override
   public Value get(Scope scope, Scope left) {
      Scope instance = binder.bind(scope, scope);
      State state = instance.getState();
      Value value = state.get(name);
      
      if(value == null) {
         Type type = instance.getType();
         VariableResult property = finder.findAll(scope, type, name);
         
         if(property != null) {
            reference.set(property);
            return property.getValue(instance);
         }
         return null;
      }
      return value;
   }
}