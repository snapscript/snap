package org.snapscript.core.function.match;

import java.util.List;

import org.snapscript.common.CopyOnWriteSparseArray;
import org.snapscript.common.SparseArray;
import org.snapscript.core.type.Type;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.index.FunctionIndex;
import org.snapscript.core.function.index.FunctionIndexBuilder;
import org.snapscript.core.function.search.FunctionPointer;
import org.snapscript.core.function.search.FunctionWrapper;
import org.snapscript.core.module.Module;
import org.snapscript.core.stack.ThreadStack;
import org.snapscript.core.type.TypeExtractor;

public class ModuleMatcher {
   
   private final SparseArray<FunctionIndex> cache;
   private final FunctionIndexBuilder builder;
   private final FunctionWrapper wrapper;
   
   public ModuleMatcher(TypeExtractor extractor, ThreadStack stack) {
      this(extractor, stack, 10000);
   }
   
   public ModuleMatcher(TypeExtractor extractor, ThreadStack stack, int capacity) {
      this.cache = new CopyOnWriteSparseArray<FunctionIndex>(capacity);
      this.builder = new FunctionIndexBuilder(extractor, stack);
      this.wrapper = new FunctionWrapper(stack);
   }

   public FunctionPointer match(Module module, String name, Type... types) throws Exception { 
      int index = module.getOrder();
      FunctionIndex match = cache.get(index);
      
      if(match == null) {
         List<Function> functions = module.getFunctions();
         FunctionIndex table = builder.create(module);
         
         for(Function function : functions){
            FunctionPointer call = wrapper.toCall(function);
            table.index(call);
         }
         cache.set(index, table);
         return table.resolve(name, types);
      }
      return match.resolve(name, types);
   }
   
   public FunctionPointer match(Module module, String name, Object... values) throws Exception { 
      int index = module.getOrder();
      FunctionIndex match = cache.get(index);
      
      if(match == null) {
         List<Function> functions = module.getFunctions();
         FunctionIndex table = builder.create(module);
         
         for(Function function : functions){
            FunctionPointer call = wrapper.toCall(function);
            table.index(call);
         }
         cache.set(index, table);
         return table.resolve(name, values);
      }
      return match.resolve(name, values);
   }
}