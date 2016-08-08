package org.snapscript.tree.variable;

import static org.snapscript.core.ModifierType.PUBLIC;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeTraverser;
import org.snapscript.core.Value;
import org.snapscript.core.property.MapProperty;
import org.snapscript.core.property.Property;
import org.snapscript.core.property.PropertyValue;

public class MapResolver implements ValueResolver<Map> {
   
   private final AtomicReference<Property> reference;
   private final ObjectResolver resolver;
   private final String name;
   
   public MapResolver(TypeTraverser extractor, String name) {
      this.resolver = new ObjectResolver(extractor, name);
      this.reference = new AtomicReference<Property>();
      this.name = name;
   }
   
   @Override
   public Value resolve(Scope scope, Map left) {
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
      Property property = resolver.match(scope, left);
   
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