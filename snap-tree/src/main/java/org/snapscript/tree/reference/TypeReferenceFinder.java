package org.snapscript.tree.reference;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.snapscript.core.Module;
import org.snapscript.core.Type;

public class TypeReferenceFinder {

   public TypeReferenceFinder() {
      super();
   }   
   
   public Type findType(Type type, String name) {
      Set<Type> done = new HashSet<Type>();
      
      if(type != null) {
         return searchEnclosing(type, name, done);
      }
      return null;
   }
   
   public Type searchHierarchy(Type type, String name, Set<Type> done) {
      List<Type> types = type.getTypes(); // do not use extractor here
      
      for(Type base : types) {
         if(done.add(base)) { // avoid loop
            Type result = searchEnclosing(base, name, done);
            
            if(result != null) {
               return result;
            }
         }
      }
      return null;
   }
   
   private Type searchEnclosing(Type type, String name, Set<Type> done) {
      Module module = type.getModule();
      
      while(type != null){ // search outer classes
         String prefix = type.getName();
         Type result = module.getType(prefix + "$"+name);
         
         if(result == null) {
            result = searchHierarchy(type, name, done);
         }
         if(result != null) {
            return result;
         }
         type = type.getOuter();
      }
      return null;
   }
}
