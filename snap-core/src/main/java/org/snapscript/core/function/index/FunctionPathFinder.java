package org.snapscript.core.function.index;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.snapscript.core.constraint.Constraint;
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

   public List<Type> findPath(Type type) {
      List<Type> path = paths.fetch(type);
      
      if(path == null) {
         List<Type> result = new ArrayList<Type>();
      
         findTypes(type, result);
         paths.cache(type, result);
         
         return result;
      }
      return path;
   }

   private void findTypes(Type type, List<Type> done) {
      Scope scope = type.getScope();
      Type base = loader.loadType(scope);
      Class real = type.getType();
      
      findClasses(type, done);
      
      if(real == null) {
         findTraits(type, done);
      }
      done.add(base); // any is very last
   }
   
   private void findTraits(Type type, List<Type> done) {
      List<Constraint> types = type.getTypes();
      Iterator<Constraint> iterator = types.iterator();
      
      if(iterator.hasNext()) {
         Scope scope = type.getScope();
         Constraint next = iterator.next(); // next in line, i.e base
         
         while(iterator.hasNext()) {
            Constraint trait = iterator.next();
            Type match = trait.getType(scope);
            
            if(!done.contains(match)) {
               done.add(match);
            }
         }
         Type match = next.getType(scope);
         
         if(!done.contains(match)) {
            findTraits(match, done);
         }
      }
   }
   
   private void findClasses(Type type, List<Type> done) {
      List<Constraint> types = type.getTypes();
      Iterator<Constraint> iterator = types.iterator();
      Scope scope = type.getScope();
      
      if(!inspector.isProxy(type) && !inspector.isAny(type)) {
         done.add(type);
      }
      while(iterator.hasNext()) {
         Constraint next = iterator.next();
         Type match = next.getType(scope);
         
         if(!done.contains(match)) {
            findClasses(match, done);
         }
      }
   }
}