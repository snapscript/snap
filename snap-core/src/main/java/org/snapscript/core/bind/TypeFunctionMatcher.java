package org.snapscript.core.bind;

import static org.snapscript.core.ModifierType.STATIC;

import java.util.List;

import org.snapscript.core.ModifierType;
import org.snapscript.core.Type;
import org.snapscript.core.TypeCache;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.convert.TypeInspector;
import org.snapscript.core.function.Function;
import org.snapscript.core.stack.ThreadStack;

public class TypeFunctionMatcher {
   
   private final TypeCache<FunctionTable> table;
   private final FunctionTableBuilder builder;
   private final FunctionPathFinder finder;
   private final FunctionSearcher searcher;
   private final TypeInspector inspector;
   private final ThreadStack stack;
   
   public TypeFunctionMatcher(TypeExtractor extractor, ThreadStack stack) {
      this.searcher = new FilterFunctionSearcher(STATIC.mask, true);
      this.builder = new FunctionTableBuilder(searcher, extractor);
      this.table = new TypeCache<FunctionTable>();
      this.finder = new FunctionPathFinder();
      this.inspector = new TypeInspector();
      this.stack = stack;
   }
   
   public FunctionPointer match(Type type, String name, Object... values) throws Exception { 
      Function function = resolve(type, name, values);
      
      if(function != null) {
         return new FunctionPointer(function, stack, values);
      }
      return null;
   }
   
   private Function resolve(Type type, String name, Object... values) throws Exception { 
      FunctionTable cache = table.fetch(type);
      
      if(cache == null) {
         List<Type> path = finder.findPath(type, name); 
         FunctionTable group = builder.create();
         int size = path.size();

         for(int i = size - 1; i >= 0; i--) {
            Type entry = path.get(i);
            List<Function> functions = entry.getFunctions();

            for(Function function : functions){
               int modifiers = function.getModifiers();
               
               if(ModifierType.isStatic(modifiers)) {
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
