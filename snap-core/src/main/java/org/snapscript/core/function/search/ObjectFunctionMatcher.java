package org.snapscript.core.function.search;

import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;

public class ObjectFunctionMatcher {
   
   private final FunctionResolver resolver;
   private final TypeExtractor extractor;
   
   public ObjectFunctionMatcher(TypeExtractor extractor, FunctionResolver resolver) {
      this.extractor = extractor;
      this.resolver = resolver;
   }

   public FunctionPointer match(Type type, String name, Type... values) throws Exception { 
      return resolver.resolve(type, name, values);
   }
   
   public FunctionPointer match(Object value, String name, Object... values) throws Exception { 
      Type type = extractor.getType(value);
      return resolver.resolve(type, name, values);
   }
}