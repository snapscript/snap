package org.snapscript.tree.variable.index;

import static org.snapscript.core.constraint.Constraint.NONE;

import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.Value;
import org.snapscript.core.type.Type;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.property.Property;
import org.snapscript.core.property.PropertyValue;
import org.snapscript.tree.variable.VariableFinder;

public class TypeStaticPointer implements VariablePointer<Type> {
   
   private final AtomicReference<Property> reference;
   private final VariableFinder finder;
   private final String name;
   
   public TypeStaticPointer(VariableFinder finder, String name) {
      this.reference = new AtomicReference<Property>();
      this.finder = finder;
      this.name = name;
   }

   @Override
   public Constraint compile(Scope scope, Constraint left) {
      Property property = reference.get();
      
      if(property == null) {
         Type type = left.getType(scope);
         Property match = finder.findAnyFromType(scope, type, name);
         
         if(match != null) {
            reference.set(match);
            return match.getConstraint();
         }
         match = finder.findAnyFromObject(scope, type, name);
         
         if(match != null) {
            reference.set(match);
            return match.getConstraint();
         }
         return NONE;
      } 
      return property.getConstraint();
   }
   
   @Override
   public Value get(Scope scope, Type left) {
      Property property = reference.get();
      
      if(property == null) {
         Property match = finder.findAnyFromType(scope, left, name);
         
         if(match != null) {
            reference.set(match);
            return new PropertyValue(match, left, name);
         }
         match = finder.findAnyFromObject(scope, left, name);
         
         if(match != null) {
            reference.set(match);
            return new PropertyValue(match, left, name);
         }
         return null;
      } 
      return new PropertyValue(property, left, name);
   }
}