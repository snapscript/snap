package org.snapscript.core.function.index;

import org.snapscript.core.type.Type;
import org.snapscript.core.module.Module;
import org.snapscript.core.stack.ThreadStack;
import org.snapscript.core.type.TypeExtractor;

public class FunctionIndexBuilder {

   private final FunctionKeyBuilder builder;
   private final FunctionReducer reducer;
   private final int limit; 
   
   public FunctionIndexBuilder(TypeExtractor extractor, ThreadStack stack) {
      this(extractor, stack, 20);
   }
   
   public FunctionIndexBuilder(TypeExtractor extractor, ThreadStack stack, int limit) {
      this.builder = new FunctionKeyBuilder(extractor);
      this.reducer = new FunctionReducer(stack);
      this.limit = limit;
   }
   
   public FunctionIndex create(Module module) {
      return new ScopeFunctionIndex(reducer, builder, limit);
   }
   
   public FunctionIndex create(Type type) {
      Class real = type.getType();
      
      if(real == null) {
         return new ScopeFunctionIndex(reducer, builder, limit);
      }
      return new ClassFunctionIndex(reducer, builder); // all functions are typed
   }
}