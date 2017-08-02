package org.snapscript.core.bind;

import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.snapscript.core.Type;
import org.snapscript.core.TypeCache;
import org.snapscript.core.convert.ProxyTypeFilter;

public class FunctionPathFinder {
   
   private final TypeCache<List<Type>> paths;
   private final ProxyTypeFilter filter;
   
   public FunctionPathFinder() {
      this.paths = new TypeCache<List<Type>>();
      this.filter = new ProxyTypeFilter();
   }

   public List<Type> findPath(Type type, String name) {
      if(name.equals(TYPE_CONSTRUCTOR)) {
         return Arrays.asList(type);
      }
      return findTypes(type, name);
   }
   
   private List<Type> findTypes(Type type, String name) {
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
      
      if(filter.accept(type)) {
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