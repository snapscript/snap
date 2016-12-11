package org.snapscript.core;

import java.util.Set;

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;

public class TypeExtractor {
   
   private final Cache<Class, Type> matches;
   private final TypeTraverser traverser;
   private final TypeLoader loader;
   
   public TypeExtractor(TypeLoader loader) {
      this.matches = new CopyOnWriteCache<Class, Type>();
      this.traverser = new TypeTraverser();
      this.loader = loader;
   }
   
   public Type getType(Object value) {
      if(value != null) {
         Class type = value.getClass();
         Type match = matches.fetch(type);
         
         if(match == null) {
            if(Handle.class.isAssignableFrom(type)) {
               Handle handle = (Handle)value;
               return handle.getHandle();
            }             
            Type actual = loader.loadType(type);
            
            if(actual != null) {
               matches.cache(type, actual);
            }
            return actual;
         }
         return match;
      }
      return null;
   }

   
   public Set<Type> getTypes(Object value) {
      Type type = getType(value);
      
      if(type != null) {
         return traverser.findHierarchy(type);
      }
      return null;
   }   
   
   public Set<Type> getTypes(Type type) {
      return traverser.findHierarchy(type);
   }
}
