package org.snapscript.core.bind;

import org.snapscript.core.Type;

public class TypeCacheIndexer implements FunctionCacheIndexer<Type> {
   
   public TypeCacheIndexer() {
      super();
   }
   
   @Override
   public int index(Type type) {
      return type.getOrder();
   }
}