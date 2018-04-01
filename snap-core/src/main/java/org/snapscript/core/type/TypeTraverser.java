package org.snapscript.core.type;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.module.Module;
import org.snapscript.core.type.Type;

public class TypeTraverser {
   
   private final TypeCache<Set<Type>> types;
   
   public TypeTraverser() {
      this.types = new TypeCache<Set<Type>>();
   }

   public Set<Type> findHierarchy(Type type) {
      Set<Type> list = types.fetch(type);
      
      if(list == null) {
         list = findHierarchy(type, type);
         types.cache(type, list);
      }
      return list;
   }
   
   private Set<Type> findHierarchy(Type root, Type type) {
      Set<Type> list = new LinkedHashSet<Type>();
      
      if(type != null) {
         findHierarchy(root, type, list);
      }
      return Collections.unmodifiableSet(list);
   }
   
   private Set<Type> findHierarchy(Type root, Type type, Set<Type> list) {
      List<Type> types = type.getTypes();
      
      if(list.add(type)) {
         for(Type entry : types) {
            if(entry == root) { 
               throw new InternalStateException("Hierarchy for '" + type + "' contains a cycle");
            }
            findHierarchy(root, entry, list);
         }
      }
      return list;
   }
   
   public Type findEnclosing(Type type, String name) {
      Set<Type> done = new LinkedHashSet<Type>();
      
      if(type != null) {
         return findEnclosing(type, name, done);
      }
      return null;
   }
   
   private Type findEnclosing(Type type, String name, Set<Type> done) {
      Module module = type.getModule();
      
      while(type != null){ // search outer classes
         String prefix = type.getName();
         Type result = module.getType(prefix + "$"+name);
         
         if(result == null) {
            result = findHierarchy(type, name, done);
         }
         if(result != null) {
            return result;
         }
         type = type.getOuter();
      }
      return null;
   }
   
   private Type findHierarchy(Type type, String name, Set<Type> done) {
      List<Type> types = type.getTypes(); // do not use extractor here
      
      for(Type base : types) {
         if(done.add(base)) { // avoid loop
            Type result = findEnclosing(base, name, done);
            
            if(result != null) {
               return result;
            }
         }
      }
      return null;
   }
}