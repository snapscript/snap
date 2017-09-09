package org.snapscript.core.bind;

import org.snapscript.core.Module;
import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;

public class FunctionTableBuilder {

   private final FunctionKeyBuilder builder;
   private final FunctionSearcher searcher;
   private final int limit; 
   
   public FunctionTableBuilder(TypeExtractor extractor) {
      this(extractor, 100);
   }
   
   public FunctionTableBuilder(TypeExtractor extractor, int limit) {
      this.builder = new FunctionKeyBuilder(extractor);
      this.searcher = new FunctionSearcher();
      this.limit = limit;
   }
   
   public FunctionTable create(Module module) {
      return new ScopeFunctionTable(searcher, builder, limit);
   }
   
   public FunctionTable create(Type type) {
      Class real = type.getType();
      
      if(real == null) {
         return new ScopeFunctionTable(searcher, builder, limit);
      }
      return new ClassFunctionTable(searcher, builder); // all functions are typed
   }
}
