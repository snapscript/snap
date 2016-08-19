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
   
   @Bug("is this even useful")
   public Type getType(Object value) {
      return getType(value, false);
   }
   
   public Type getType(Object value, boolean variable) {
      if(value != null) {
         Class type = value.getClass();
         Type match = matches.fetch(type);
         
         if(match == null) {
            if(!variable) {
               if(Handle.class.isAssignableFrom(type)) {
                  Handle handle = (Handle)value;
                  return handle.getHandle();
               }             
            } else {
               if(Scope.class.isAssignableFrom(type)){
                  Scope scope = (Scope)value;
                  return scope.getType();
               }
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
         return traverser.traverse(type);
      }
      return null;
   }   
   
   public Set<Type> getTypes(Type type) {
      return traverser.traverse(type);
   }
}
