package org.snapscript.core.function.find;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.snapscript.core.Type;
import org.snapscript.core.TypeCache;
import org.snapscript.core.convert.TypeInspector;

public class FunctionPathFinder {
   
   private final TypeCache<List<Type>> paths;
   private final TypeInspector filter;
   
   public FunctionPathFinder() {
      this.paths = new TypeCache<List<Type>>();
      this.filter = new TypeInspector();
   }

   public List<Type> findPath(Type type, String name) {
      List<Type> path = paths.fetch(type);
      Class real = type.getType();
      
      if(path == null) {
         List<Type> result = new ArrayList<Type>();
      
         findClasses(type, result);
      
         if(real == null) {
            findTraits(type, result);
         }
         paths.cache(type, result);
         return result;
      }
      return path;
   }
   
   private void findTraits(Type type, List<Type> done) {
      List<Type> types = type.getTypes();
      Iterator<Type> iterator = types.iterator();
      
      if(iterator.hasNext()) {
         Type next = iterator.next(); // next in line, i.e base
         
         for(Type entry : types) {
            if(!done.contains(entry)) {
               done.add(entry);
            }
         }
         findTraits(next, done);
      }
   }
   
   private void findClasses(Type type, List<Type> done) {
      List<Type> types = type.getTypes();
      Iterator<Type> iterator = types.iterator();
      
      if(!filter.isProxy(type)) {
         done.add(type);
      }
      while(iterator.hasNext()) {
         Type next = iterator.next();
         
         if(!done.contains(next)) {
            findClasses(next, done);
         }
      }
   }
}