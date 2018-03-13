package org.snapscript.tree.variable;

import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

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
   public Type check(Scope scope, Type left) {
      Property property = reference.get();
      
      if(property == null) {
         Module module = scope.getModule();
         Context context = module.getContext();
         TypeExtractor extractor = context.getExtractor();
         Set<Type> list = extractor.getTypes(left);
         
         for(Type base : list) {
            Property match = pointer.match(scope, left, base);
            
            if(match != null) {
               reference.set(match);
               return match.getConstraint().getType(scope);
            }
         } 
         Property match = pointer.match(scope, left);
         
         if(match != null) {
            reference.set(match);
            return match.getConstraint().getType(scope);
         }
         return null;
      } 
      return property.getConstraint().getType(scope);
   }
   
   @Override
   public Value get(Scope scope, Type left) {
      Property property = reference.get();
      
      if(property == null) {
         Module module = scope.getModule();
         Context context = module.getContext();
         TypeExtractor extractor = context.getExtractor();
         Set<Type> list = extractor.getTypes(left);
         
         for(Type base : list) {
            Property match = pointer.match(scope, left, base);
            
            if(match != null) {
               reference.set(match);
               return new PropertyValue(match, left, name);
            }
         } 
         Property match = pointer.match(scope, left);
         
         if(match != null) {
            reference.set(match);
            return new PropertyValue(match, left, name);
         }
         return null;
      } 
      return new PropertyValue(property, left, name);
   }
}