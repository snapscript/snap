package org.snapscript.tree.variable;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Type;
import org.snapscript.core.TypeTraverser;
import org.snapscript.core.Value;
import org.snapscript.core.property.Property;
import org.snapscript.core.property.PropertyValue;

public class ScopeResolver implements ValueResolver<Scope> {
   
   private final AtomicReference<Property> reference;
   private final TypeTraverser traverser;
   private final String name;
   
   public ScopeResolver(TypeTraverser traverser, String name) {
      this.reference = new AtomicReference<Property>();
      this.traverser = traverser;
      this.name = name;
   }
   
   @Override
   public Value resolve(Scope scope, Scope left) {
      State state = left.getState();
      Value value = state.getValue(name);
      
      if(value == null) {
         Type type = left.getType();
         
         if(type != null) {
            Property property = match(scope, left);
            
            if(property != null) {
               reference.set(property);
               return new PropertyValue(property, left, name);
            }
         }
      }
      return value;
   }
   
   private Property match(Scope scope, Scope left) {
      Property property = reference.get();
      
      if(property == null) {
         Type source = left.getType();
         Set<Type> list = traverser.traverse(source);
         
         for(Type base : list) {
            Property match = match(scope, left, base);
            
            if(match != null) {
               return match;
            }
         }
      }
      return property;
   }
   
   private Property match(Scope scope, Object left, Type type) {
      List<Property> properties = type.getProperties();
      
      for(Property property : properties){
         String field = property.getName();
         
         if(field.equals(name)) {
            return property;
         }
      } 
      return null;
   }
}