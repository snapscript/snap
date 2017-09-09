package org.snapscript.core.bind;

import org.snapscript.core.Type;
import org.snapscript.core.TypeCache;
import org.snapscript.core.function.Function;

public class FunctionTable {
   
   private final TypeCache<FunctionGroupTable> cache;
   private final FunctionKeyBuilder builder;
   private final FunctionMatcher matcher;
   
   public FunctionTable(FunctionMatcher matcher, FunctionKeyBuilder builder) {      
      this.cache = new TypeCache<FunctionGroupTable>();
      this.matcher = matcher;
      this.builder = builder;
   }

   public Function resolve(Type type, String name, Object... list) throws Exception {
      FunctionGroupTable table = cache.fetch(type);
      
      if(table == null) {
         table = new FunctionGroupTable(matcher, builder);
         cache.cache(type, table);
      }
      return table.resolve(name, list);
   }
}
