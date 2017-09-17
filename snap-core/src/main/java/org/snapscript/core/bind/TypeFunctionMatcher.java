package org.snapscript.core.bind;

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
   private final FunctionWrapper wrapper;
   private final TypeInspector inspector;
   
   public TypeFunctionMatcher(TypeExtractor extractor, ThreadStack stack) {
      this.builder = new FunctionTableBuilder(extractor, stack);
      this.table = new TypeCache<FunctionTable>();
      this.wrapper = new FunctionWrapper(stack);
      this.finder = new FunctionPathFinder();
      this.inspector = new TypeInspector();
   }
   
   public FunctionCall match(Type type, String name, Object... values) throws Exception { 
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
               
               if(ModifierType.isStatic(modifiers)) {
                  if(!inspector.isSuperConstructor(type, function)) {
                     FunctionCall call = wrapper.toCall(function);
                     group.update(call);
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
