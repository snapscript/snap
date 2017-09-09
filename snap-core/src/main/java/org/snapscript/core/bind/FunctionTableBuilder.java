package org.snapscript.core.bind;

import org.snapscript.core.TypeExtractor;

public class FunctionTableBuilder {

   private final FunctionKeyBuilder builder;
   private final FunctionSearcher searcher;
   private final int filter; // filter modifiers
   private final int limit; 
   
   public FunctionTableBuilder(FunctionSearcher searcher, TypeExtractor extractor) {
      this(searcher, extractor, 0);
   }
   
   public FunctionTableBuilder(FunctionSearcher searcher, TypeExtractor extractor, int filter) {
      this(searcher, extractor, filter, 200);
   }
   
   public FunctionTableBuilder(FunctionSearcher searcher, TypeExtractor extractor, int filter, int limit) {
      this.builder = new FunctionKeyBuilder(extractor);
      this.searcher = searcher;
      this.filter = filter;
      this.limit = limit;
   }
   
   public FunctionTable create() {
      return new FunctionTable(searcher, builder, filter, limit);
   }
}
