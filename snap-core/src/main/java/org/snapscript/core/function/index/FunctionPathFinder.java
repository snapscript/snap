package org.snapscript.core.function.index;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.snapscript.core.convert.TypeInspector;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.AnyLoader;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeCache;

public class FunctionPathFinder {
   
   private final TypeCache<List<Type>> paths;  
   private final TypeInspector inspector;  
   private final AnyLoader loader;
   
   public FunctionPathFinder() {
      this.paths = new TypeCache<List<Type>>();
      this.inspector = new TypeInspector();
      this.loader = new AnyLoader();
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
         Scope scope = type.getScope();
         Type base = loader.loadType(scope);
         
         result.add(base); // any is very last
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
      
      if(!inspector.isProxy(type) && !inspector.isAny(type)) {
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