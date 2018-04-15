package org.snapscript.core.variable.index;

import static org.snapscript.core.constraint.Constraint.NONE;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.property.Property;
import org.snapscript.core.property.PropertyValue;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;
import org.snapscript.core.variable.bind.VariableFinder;

public class MapPointer implements VariablePointer<Map> {
   
   private final AtomicReference<Property> reference;
   private final VariableFinder finder;
   private final String name;
   
   public MapPointer(VariableFinder finder, String name) {
      this.reference = new AtomicReference<Property>();
      this.finder = finder;
      this.name = name;
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint left) {
      Property accessor = reference.get();
      
      if(accessor == null) {
         Type type = left.getType(scope);
         Property match = finder.findProperty(scope, type, name);
         
         if(match != null) {
            reference.set(match);
            return match.getConstraint();
         }
         return NONE;
      }
      return accessor.getConstraint();
   }
   
   @Override
   public Value get(Scope scope, Map left) {
      Property accessor = reference.get();
      
      if(accessor == null) {
         Property match = finder.findProperty(scope, left, name);
         
         if(match != null) {
            reference.set(match);
            return new PropertyValue(match, left, name);
         }
         return null;
      }
      return new PropertyValue(accessor, left, name);
   }
}