package org.snapscript.tree.variable;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeTraverser;
import org.snapscript.core.Value;
import org.snapscript.core.property.Property;
import org.snapscript.core.property.PropertyValue;

public class ObjectResolver implements ValueResolver<Object> {
   
   private final AtomicReference<Property> reference;
   private final TypeTraverser traverser;
   private final String name;
   
   public ObjectResolver(TypeTraverser extractor, String name) {
      this.reference = new AtomicReference<Property>();
      this.traverser = extractor;
      this.name = name;
   }
   
   @Override
   public Value resolve(Scope scope, Object left) {
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
      Module module = scope.getModule();
      Class type = left.getClass();
      String alias = type.getName();
      Type source = module.getType(alias);
      Set<Type> list = traverser.traverse(source);
      
      for(Type base : list) {
         Property match = match(scope, left, base);
         
         if(match != null) {
            return match;
         }
      }
      return null;
   }
   
   public Property match(Scope scope, Object left, Type type) {
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