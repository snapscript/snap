package org.snapscript.core.bind;

import java.util.List;

import org.snapscript.core.ModifierType;
import org.snapscript.core.Type;
import org.snapscript.core.TypeCache;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.convert.TypeInspector;
import org.snapscript.core.function.Function;

public class FunctionResolver {
   
   private final TypeCache<FunctionTable> table;
   private final FunctionTableBuilder builder;
   private final FunctionPathFinder finder;
   private final TypeInspector inspector;
   
   public FunctionResolver(TypeExtractor extractor) {
      this.builder = new FunctionTableBuilder(extractor);
      this.table = new TypeCache<FunctionTable>();
      this.finder = new FunctionPathFinder();
      this.inspector = new TypeInspector();
   }
   
   public Function resolve(Type type, String name, Type... types) throws Exception { 
      FunctionTable cache = table.fetch(type);

      if(cache == null) {
         List<Type> path = finder.findPath(type, name); 
         FunctionTable group = builder.create(type);
         int size = path.size();

         for(int i = size - 1; i >= 0; i--) {
            Type entry = path.get(i);
            List<Function> functions = entry.getFunctions();

            for(Function function : functions){
               int modifiers = function.getModifiers();
               
               if(!ModifierType.isAbstract(modifiers)) {
                  if(!inspector.isSuperConstructor(type, function)) {
                     group.update(function);
                  }
               }
            }
         }
         table.cache(type, group);
         return group.resolve(name, types);
      }
      return cache.resolve(name, types);
   }

   public Function resolve(Type type, String name, Object... values) throws Exception { 
      FunctionTable cache = table.fetch(type);

      if(cache == null) {
         List<Type> path = finder.findPath(type, name); 
         FunctionTable group = builder.create(type);
         int size = path.size();

         for(int i = size - 1; i >= 0; i--) {
            Type entry = path.get(i);
            List<Function> functions = entry.getFunctions();

            for(Function function : functions){
               int modifiers = function.getModifiers();
               
               if(!ModifierType.isAbstract(modifiers)) {
                  if(!inspector.isSuperConstructor(type, function)) {
                     group.update(function);
                  }
               }
            }
         }
         table.cache(type, group);
         return group.resolve(name, values);
      }
      return cache.resolve(name, values);
   }
}
