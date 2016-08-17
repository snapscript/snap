package org.snapscript.core;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class TypeTraverser {
   
   private final TypeCache<Set<Type>> types;
   
   public TypeTraverser() {
      this.types = new TypeCache<Set<Type>>();
   }

   public Set<Type> traverse(Type type) {
      Set<Type> list = types.fetch(type);
      
      if(list == null) {
         list = collect(type);
         types.cache(type, list);
      }
      return list;
   }
   
   private Set<Type> collect(Type type) {
      Set<Type> list = new LinkedHashSet<Type>();
      
      if(type != null) {
         collect(type, type, list);
      }
      return Collections.unmodifiableSet(list);
   }
   
   private Set<Type> collect(Type root, Type type, Set<Type> list) {
      List<Type> types = type.getTypes();
      
      if(list.add(type)) {
         for(Type entry : types) {
            if(entry == root) { 
               throw new InternalStateException("Hierarchy for '" + type + "' contains a cycle");
            }
            collect(root, entry, list);
         }
      }
      return list;
   }
}
