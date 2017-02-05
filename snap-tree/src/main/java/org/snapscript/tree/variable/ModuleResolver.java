package org.snapscript.tree.variable;

import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.core.property.Property;
import org.snapscript.core.property.PropertyValue;

public class ModuleResolver implements ValueResolver<Module> {
   
   private final AtomicReference<Property> reference;
   private final ObjectResolver resolver;
   private final String name;
   
   public ModuleResolver(String name) {
      this.reference = new AtomicReference<Property>();
      this.resolver = new ObjectResolver(name);
      this.name = name;
   }
   
   @Override
   public Value resolve(Scope scope, Module left) {
      Property property = reference.get();
      
      if(property == null) {
         Scope inner = left.getScope();
         State state = inner.getState();
         Value value = state.get(name);
         
         if(value == null) {
            Type type = left.getType(name);
           
            if(type != null) {
               return ValueType.getTransient(type);
            }
            Property match = resolver.match(scope, left);
            
            if(match != null) {
               reference.set(match);
               return new PropertyValue(match, left, name);
            }
         }
         return value;
      } 
      return new PropertyValue(property, left, name);
   }
}