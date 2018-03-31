package org.snapscript.core.function.search;

import org.snapscript.core.Module;
import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.stack.ThreadStack;

public class FunctionIndexBuilder {

   private final FunctionKeyBuilder builder;
   private final FunctionScanner searcher;
   private final int limit; 
   
   public FunctionIndexBuilder(TypeExtractor extractor, ThreadStack stack) {
      this(extractor, stack, 20);
   }
   
   public FunctionIndexBuilder(TypeExtractor extractor, ThreadStack stack, int limit) {
      this.builder = new FunctionKeyBuilder(extractor);
      this.searcher = new FunctionScanner(stack);
      this.limit = limit;
   }
   
   public FunctionIndex create(Module module) {
      return new ScopeFunctionIndex(searcher, builder, limit);
   }
   
   public FunctionIndex create(Type type) {
      Class real = type.getType();
      
      if(real == null) {
         return new ScopeFunctionIndex(searcher, builder, limit);
      }
      return new ClassFunctionIndex(searcher, builder); // all functions are typed
   }
}