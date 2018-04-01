package org.snapscript.tree.variable;

import static org.snapscript.core.constraint.Constraint.NONE;

import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.scope.Value;
import org.snapscript.core.type.Type;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.property.Property;
import org.snapscript.core.property.PropertyValue;
import org.snapscript.tree.define.ThisScopeBinder;

public class ScopePointer implements VariablePointer<Scope> {
   
   private final AtomicReference<Property> reference;
   private final ThisScopeBinder binder;
   private final VariableFinder finder;
   private final String name;
   
   public ScopePointer(VariableFinder finder, String name) {
      this.reference = new AtomicReference<Property>();
      this.binder = new ThisScopeBinder();
      this.finder = finder;
      this.name = name;
   }
   
   @Override
   public Constraint check(Scope scope, Constraint left) {
      Type type = left.getType(scope);
      Scope instance = type.getScope();
      State state = instance.getState();
      Value value = state.get(name);
      
      if(value == null) {
         Property property = finder.findPropertyFromScope(scope, instance, name);
         
         if(property != null) {
            reference.set(property);
            return new PropertyValue(property, instance, name);
         }
         return null;
      }
      return value;
   }
   
   @Override
   public Value get(Scope scope, Scope left) {
      Scope instance = binder.bind(left, left);
      State state = instance.getState();
      Value value = state.get(name);
      
      if(value == null) {
         Type type = left.getType();
         
         if(type != null) {
            Property property = finder.findPropertyFromScope(scope, instance, name);
            
            if(property != null) {
               reference.set(property);
               return new PropertyValue(property, instance, name);
            }
         }
      }
      return value;
   }
}