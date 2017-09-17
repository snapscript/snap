package org.snapscript.core.bind;

import java.util.List;

import org.snapscript.common.CopyOnWriteSparseArray;
import org.snapscript.common.SparseArray;
import org.snapscript.core.Module;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.function.Function;
import org.snapscript.core.stack.ThreadStack;

public class ModuleFunctionMatcher {
   
   private final SparseArray<FunctionTable> cache;
   private final FunctionTableBuilder builder;
   private final FunctionWrapper wrapper;
   
   public ModuleFunctionMatcher(TypeExtractor extractor, ThreadStack stack) {
      this(extractor, stack, 10000);
   }
   
   public ModuleFunctionMatcher(TypeExtractor extractor, ThreadStack stack, int capacity) {
      this.cache = new CopyOnWriteSparseArray<FunctionTable>(capacity);
      this.builder = new FunctionTableBuilder(extractor, stack);
      this.wrapper = new FunctionWrapper(stack);
   }
   
   public FunctionCall match(Module module, String name, Object... values) throws Exception { 
      int index = module.getOrder();
      FunctionTable match = cache.get(index);
      
      if(match == null) {
         List<Function> functions = module.getFunctions();
         FunctionTable table = builder.create(module);
         
         for(Function function : functions){
            FunctionCall call = wrapper.toCall(function);
            table.update(call);
         }
         cache.set(index, table);
         return table.resolve(name, values);
      }
      return match.resolve(name, values);
   }
}