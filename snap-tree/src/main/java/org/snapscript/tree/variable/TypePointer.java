package org.snapscript.tree.variable;

import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.Constraint;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.property.Property;
import org.snapscript.core.property.PropertyValue;

public class TypePointer implements VariablePointer<Type> {
   
   private final AtomicReference<Property> reference;
   private final ConstantResolver resolver;
   private final String name;
   
   public TypePointer(ConstantResolver resolver, String name) {
      this.reference = new AtomicReference<Property>();
      this.resolver = resolver;
      this.name = name;
   }

   @Override
   public Constraint check(Scope scope, Constraint x) {
      Property property = reference.get();
      
      if(property == null) {
         Type t=x.getType(scope);
         Property match = resolver.matchFromType(scope, t, name);
         
         if(match != null) {
            reference.set(match);
            return match.getConstraint();
         }
         match = resolver.matchFromObject(scope, t, name);
         
         if(match != null) {
            reference.set(match);
            return match.getConstraint();
         }
         return Constraint.getNone();
      } 
      return property.getConstraint();
   }
   
   @Override
   public Value get(Scope scope, Type left) {
      Property property = reference.get();
      
      if(property == null) {
         Property match = resolver.matchFromType(scope, left, name);
         
         if(match != null) {
            reference.set(match);
            return new PropertyValue(match, left, name);
         }
         match = resolver.matchFromObject(scope, left, name);
         
         if(match != null) {
            reference.set(match);
            return new PropertyValue(match, left, name);
         }
         return null;
      } 
      return new PropertyValue(property, left, name);
   }
}