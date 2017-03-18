
package org.snapscript.tree.variable;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.Value;
import org.snapscript.core.property.Property;
import org.snapscript.core.property.PropertyValue;

public class ScopeResolver implements ValueResolver<Scope> {
   
   private final AtomicReference<Property> reference;
   private final String name;
   
   public ScopeResolver(String name) {
      this.reference = new AtomicReference<Property>();
      this.name = name;
   }
   
   @Override
   public Value resolve(Scope scope, Scope left) {
      State state = left.getState();
      Value value = state.get(name);
      
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
         Module module = scope.getModule();
         Context context = module.getContext();
         TypeExtractor extractor = context.getExtractor();
         Set<Type> list = extractor.getTypes(source);
         
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