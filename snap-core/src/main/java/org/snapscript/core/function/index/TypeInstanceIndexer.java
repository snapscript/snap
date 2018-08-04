package org.snapscript.core.function.index;

import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeExtractor;
import org.snapscript.core.variable.Value;

public class TypeInstanceIndexer {
   
   private final FunctionIndexer indexer;
   private final TypeExtractor extractor;
   
   public TypeInstanceIndexer(TypeExtractor extractor, FunctionIndexer indexer) {
      this.extractor = extractor;
      this.indexer = indexer;
   }

   public FunctionPointer index(Type type, String name, Type... values) throws Exception { 
      return indexer.index(type, name, values);
   }
   
   public FunctionPointer index(Value value, String name, Object... values) throws Exception {
      Object object = value.getValue();
      Type type = extractor.getType(object);
      
      return indexer.index(type, name, values);
   }
}