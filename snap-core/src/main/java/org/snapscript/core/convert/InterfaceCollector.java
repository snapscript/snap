package org.snapscript.core.convert;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.snapscript.core.Any;
import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeCache;
import org.snapscript.core.TypeExtractor;

public class InterfaceCollector {

   private final TypeCache<Class[]> cache;
   private final Class[] include;
   private final Class[] empty;
   
   public InterfaceCollector(Class... include) {
      this.cache = new TypeCache<Class[]>();
      this.empty = new Class[]{};
      this.include = include;
   }
   
   public Class[] collect(Scope scope) {
      Type type = scope.getType();
      
      if(type != null) {
         Class[] interfaces = cache.fetch(type);
         
         if(interfaces == null) {
            Set<Class> types = traverse(type);
            Class[] result = types.toArray(empty);
            
            cache.cache(type, result);
            return result;
         }
         return interfaces;
      }
      return empty;
   }
   
   public Class[] filter(Class... types) {
      if(types.length > 0) {
         Set<Class> interfaces = new HashSet<Class>();
         
         for(Class entry : types) {
            if(entry != null) {
               if(entry.isInterface()) {
                  interfaces.add(entry);
               }
            }
         }
         return interfaces.toArray(empty);
      }
      return empty;
   }
   
   private Set<Class> traverse(Type type) {
      Module module = type.getModule();
      Context context = module.getContext();
      TypeExtractor extractor = context.getExtractor();
      Set<Type> types = extractor.getTypes(type);
      
      if(!types.isEmpty()) {
         Set<Class> interfaces = new HashSet<Class>();
      
         for(Type entry : types) {
            Class part = entry.getType();
            
            if(part != null) {
               if(part.isInterface()) {
                  interfaces.add(part);
               }
            }
         }
         for(Class entry : include) {
            interfaces.add(entry);
         }
         interfaces.add(Any.class);
         return interfaces;
      }
      return Collections.<Class>singleton(Any.class);
   }
}