package org.snapscript.core.bind;

import java.util.List;

import org.snapscript.core.ModifierType;
import org.snapscript.core.Type;
import org.snapscript.core.TypeCache;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.convert.TypeInspector;
import org.snapscript.core.function.Function;
import org.snapscript.core.stack.ThreadStack;

public class FunctionResolver {
   
   private final TypeCache<FunctionTable> cache;
   private final FunctionTableBuilder builder;
   private final FunctionPathFinder finder;
   private final FunctionWrapper wrapper;
   private final TypeInspector inspector;
   
   public FunctionResolver(TypeExtractor extractor, ThreadStack stack) {
      this.builder = new FunctionTableBuilder(extractor, stack);
      this.cache = new TypeCache<FunctionTable>();
      this.wrapper = new FunctionWrapper(stack);
      this.finder = new FunctionPathFinder();
      this.inspector = new TypeInspector();
   }
   
   public FunctionCall resolve(Type type, String name, Type... types) throws Exception { 
      FunctionTable match = cache.fetch(type);

      if(match == null) {
         List<Type> path = finder.findPath(type, name); 
         FunctionTable table = builder.create(type);
         int size = path.size();

         for(int i = size - 1; i >= 0; i--) {
            Type entry = path.get(i);
            List<Function> functions = entry.getFunctions();

            for(Function function : functions){
               int modifiers = function.getModifiers();
               
               if(!ModifierType.isAbstract(modifiers)) {
                  if(!inspector.isSuperConstructor(type, function)) {
                     FunctionCall call = wrapper.toCall(function);
                     table.update(call);
                  }
               }
            }
         }
         cache.cache(type, table);
         return table.resolve(name, types);
      }
      return match.resolve(name, types);
   }

   public FunctionCall resolve(Type type, String name, Object... values) throws Exception { 
      FunctionTable match = cache.fetch(type);

      if(match == null) {
         List<Type> path = finder.findPath(type, name); 
         FunctionTable table = builder.create(type);
         int size = path.size();

         for(int i = size - 1; i >= 0; i--) {
            Type entry = path.get(i);
            List<Function> functions = entry.getFunctions();

            for(Function function : functions){
               int modifiers = function.getModifiers();
               
               if(!ModifierType.isAbstract(modifiers)) {
                  if(!inspector.isSuperConstructor(type, function)) {
                     FunctionCall call = wrapper.toCall(function);
                     table.update(call);
                  }
               }
            }
         }
         cache.cache(type, table);
         return table.resolve(name, values);
      }
      return match.resolve(name, values);
   }
}
