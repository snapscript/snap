package org.snapscript.tree.variable;

import static org.snapscript.core.ModifierType.PUBLIC;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.AnyType;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.property.MapProperty;
import org.snapscript.core.property.Property;
import org.snapscript.core.property.PropertyValue;

public class MapPointer implements VariablePointer<Map> {
   
   private final AtomicReference<Property> reference;
   private final ObjectPointer pointer;
   private final String name;
   
   public MapPointer(ConstantResolver resolver, String name) {
      this.reference = new AtomicReference<Property>();
      this.pointer = new ObjectPointer(resolver, name);
      this.name = name;
   }
   
   @Override
   public Type check(Scope scope, Type left) {
      Property accessor = reference.get();
      
      if(accessor == null) {
         Property match = match(scope, left);
         
         if(match != null) {
            reference.set(match);
            return match.getConstraint().getType(scope);
         }
         return left.getModule().getType(Object.class);
      }
      return accessor.getConstraint().getType(scope);
   }
   
   @Override
   public Value get(Scope scope, Map left) {
      Property accessor = reference.get();
      
      if(accessor == null) {
         Property match = match(scope, left);
         
         if(match != null) {
            reference.set(match);
            return new PropertyValue(match, left, name);
         }
         return null;
      }
      return new PropertyValue(accessor, left, name);
   }
      
   public Property match(Scope scope, Object left) {
      Property property = pointer.match(scope, left);
   
      if(property == null) {
         Module module = scope.getModule();
         Class type = left.getClass();
         String alias = type.getName();
         Type source = module.getType(alias);
         
         return new MapProperty(name, source, PUBLIC.mask);
      }
      return property;
   }
}