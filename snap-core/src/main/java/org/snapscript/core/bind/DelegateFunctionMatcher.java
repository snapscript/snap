package org.snapscript.core.bind;

import java.util.List;

import org.snapscript.core.Type;
import org.snapscript.core.TypeCache;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.convert.Delegate;
import org.snapscript.core.convert.TypeInspector;
import org.snapscript.core.function.Function;
import org.snapscript.core.stack.ThreadStack;

public class DelegateFunctionMatcher {
   
   private final TypeCache<FunctionTable> table;
   private final FunctionTableBuilder builder;
   private final FunctionPathFinder finder;
   private final FunctionWrapper wrapper;
   private final TypeExtractor extractor;
   private final TypeInspector checker;
   
   public DelegateFunctionMatcher(TypeExtractor extractor, ThreadStack stack) {
      this.builder = new FunctionTableBuilder(extractor, stack);
      this.wrapper = new FunctionWrapper(stack);
      this.table = new TypeCache<FunctionTable>();
      this.finder = new FunctionPathFinder();
      this.checker = new TypeInspector();
      this.extractor = extractor;
   }
   
   public FunctionCall match(Delegate value, String name, Object... values) throws Exception { 
      Type type = extractor.getType(value);
      FunctionTable cache = table.fetch(type);
      
      if(cache == null) {
         List<Type> path = finder.findPath(type, name); 
         FunctionTable group = builder.create(type);
         int size = path.size();

         for(int i = size - 1; i >= 0; i--) {
            Type entry = path.get(i);
            
            if(!checker.isProxy(entry)) {
               List<Function> functions = entry.getFunctions();
   
               for(Function function : functions){
                  if(!checker.isSuperConstructor(type, function)) {
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