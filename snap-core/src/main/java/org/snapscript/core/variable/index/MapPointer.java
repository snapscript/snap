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
import org.snapscript.core.variable.bind.VariableResult;

public class MapPointer implements VariablePointer<Map> {
   
   private final AtomicReference<VariableResult> reference;
   private final VariableFinder finder;
   private final String name;
   
   public MapPointer(VariableFinder finder, String name) {
      this.reference = new AtomicReference<VariableResult>();
      this.finder = finder;
      this.name = name;
   }
   
   @Override
   public Constraint getConstraint(Scope scope, Constraint left) {
      VariableResult accessor = reference.get();
      
      if(accessor == null) {
         Type type = left.getType(scope);
         VariableResult match = finder.findProperty(scope, type, name);
         
         if(match != null) {
            reference.set(match);
            return match.getConstraint(left);
         }
         return NONE;
      }
      return accessor.getConstraint(left);
   }
   
   @Override
   public Value get(Scope scope, Map left) {
      VariableResult accessor = reference.get();
      
      if(accessor == null) {
         VariableResult match = finder.findProperty(scope, left, name);
         
         if(match != null) {
            reference.set(match);
            return match.getValue(left);
         }
         return null;
      }
      return accessor.getValue(left);
   }
}