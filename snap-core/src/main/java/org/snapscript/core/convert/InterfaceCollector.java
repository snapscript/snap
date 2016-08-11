package org.snapscript.core.convert;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.snapscript.core.Any;
import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;

public class InterfaceCollector {

   private final Map<Type, Class[]> cache;
   private final Class[] empty;
   
   public InterfaceCollector() {
      this.cache = new ConcurrentHashMap<Type, Class[]>();
      this.empty = new Class[]{};
   }
   
   public Class[] collect(Scope scope) {
      Type type = scope.getType();
      
      if(type != null) {
         Class[] interfaces = cache.get(type);
         
         if(interfaces == null) {
            Set<Class> types = traverse(type);
            Class[] result = types.toArray(empty);
            
            cache.put(type, result);
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
      
      try {
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
            interfaces.add(Any.class);
            return interfaces;
         }
      } catch(Exception e) {
         return Collections.<Class>singleton(Any.class);
      }
      return Collections.<Class>singleton(Any.class);
   }
}
