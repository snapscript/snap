package org.snapscript.core.property;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeCache;
import org.snapscript.core.type.TypeExtractor;

public class PropertyExtractor {

   private final TypeCache<Set<Property>> cache;
   private final TypeExtractor extractor;
   
   public PropertyExtractor(TypeExtractor extractor) {
      this.cache = new TypeCache<Set<Property>>();
      this.extractor = extractor;
   }
   
   public Set<Property> findProperties(Type type) {
      Set<Property> properties = cache.fetch(type);
      
      if(properties == null) {
         properties = findHierarchy(type);
         cache.cache(type, properties);
      }
      return properties;
   }
   
   private Set<Property> findHierarchy(Type type) {
      Set<Type> types = extractor.getTypes(type);
      
      if(!types.isEmpty()) {
         Set<String> done = new LinkedHashSet<String>();
         Set<Property> result = new LinkedHashSet<Property>();
         
         for(Type base : types) {
            Set<Property> map = findProperties(base, done);
            result.addAll(map);
         }
         return result;
      }
      return Collections.emptySet();
   }
   
   private Set<Property> findProperties(Type type, Set<String> done) {
      List<Property> properties = type.getProperties();
      
      if(!properties.isEmpty()) {
         Set<Property> result = new LinkedHashSet<Property>();
         
         for(Property property : properties) {
            String name = property.getName();
            
            if(done.add(name)) {
               result.add(property);
            }
         }
         return result;
      }
      return Collections.emptySet();
   }
}
