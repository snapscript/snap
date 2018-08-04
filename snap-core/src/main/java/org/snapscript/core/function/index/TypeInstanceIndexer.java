package org.snapscript.core.function.index;

import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;

public class TypeInstanceIndexer {
   
   private final FunctionIndexer indexer;
   
   public TypeInstanceIndexer(FunctionIndexer indexer) {
      this.indexer = indexer;
   }

   public FunctionPointer index(Type type, String name, Type... values) throws Exception { 
      return indexer.index(type, name, values);
   }
   
   public FunctionPointer index(Value value, String name, Object... values) throws Exception {
      Type type = value.getData().getType();      
      return indexer.index(type, name, values);
   }
}