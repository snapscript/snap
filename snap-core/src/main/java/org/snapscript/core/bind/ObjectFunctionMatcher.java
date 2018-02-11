package org.snapscript.core.bind;

import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;

public class ObjectFunctionMatcher {
   
   private final FunctionResolver resolver;
   private final TypeExtractor extractor;
   
   public ObjectFunctionMatcher(TypeExtractor extractor, FunctionResolver resolver) {
      this.extractor = extractor;
      this.resolver = resolver;
   }

   public FunctionCall match(Type type, String name, Type... values) throws Exception { 
      return resolver.resolve(type, name, values);
   }
   
   public FunctionCall match(Object value, String name, Object... values) throws Exception { 
      Type type = extractor.getType(value);
      return resolver.resolve(type, name, values);
   }
}