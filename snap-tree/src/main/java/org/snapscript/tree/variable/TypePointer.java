package org.snapscript.tree.variable;

import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.Constraint;
import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.Value;
import org.snapscript.core.property.Property;
import org.snapscript.core.property.PropertyValue;

public class TypePointer implements VariablePointer<Type> {
   
   private final AtomicReference<Property> reference;
   private final ObjectPointer pointer;
   private final String name;
   
   public TypePointer(ConstantResolver resolver, String name) {
      this.reference = new AtomicReference<Property>();
      this.pointer = new ObjectPointer(resolver, name);
      this.name = name;
   }

   @Override
   public Constraint check(Scope scope, Constraint x) {
      Property property = reference.get();
      
      if(property == null) {
         Type t=x.getType(scope);
         Property match = pointer.match(scope, t, t);
         
         if(match != null) {
            reference.set(match);
            return match.getConstraint();
         }
         match = pointer.match(scope, t);
         
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
         Property match = pointer.match(scope, left, left);
         
         if(match != null) {
            reference.set(match);
            return new PropertyValue(match, left, name);
         }
         match = pointer.match(scope, left);
         
         if(match != null) {
            reference.set(match);
            return new PropertyValue(match, left, name);
         }
         return null;
      } 
      return new PropertyValue(property, left, name);
   }
}