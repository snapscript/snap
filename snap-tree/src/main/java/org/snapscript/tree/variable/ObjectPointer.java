package org.snapscript.tree.variable;

import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.Value;
import org.snapscript.core.type.Type;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.property.Property;
import org.snapscript.core.property.PropertyValue;

public class ObjectPointer implements VariablePointer<Object> {
   
   private final AtomicReference<Property> reference;
   private final VariableFinder finder;
   private final String name;
   
   public ObjectPointer(VariableFinder finder, String name) {
      this.reference = new AtomicReference<Property>();
      this.finder = finder;
      this.name = name;
   }
   
   @Override
   public Constraint check(Scope scope, Constraint left) {
      Property accessor = reference.get();
      
      if(accessor == null) {
         Type type = left.getType(scope);
         Property match = finder.findAnyFromType(scope, type, name);
         
         if(match != null) {
            reference.set(match);
            return match.getConstraint();
         }
         return null;
      }
      return accessor.getConstraint();
   }
   
   @Override
   public Value get(Scope scope, Object left) {
      Property accessor = reference.get();
      
      if(accessor == null) {
         Property match = finder.findAnyFromObject(scope, left, name);
         
         if(match != null) {
            reference.set(match);
            return new PropertyValue(match, left, name);
         }
         return null;
      }
      return new PropertyValue(accessor, left, name);
   }

}