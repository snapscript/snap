package org.snapscript.core.function.match;

import org.snapscript.core.type.Type;
import org.snapscript.core.function.search.FunctionPointer;
import org.snapscript.core.function.search.FunctionResolver;
import org.snapscript.core.type.TypeExtractor;

public class TypeInstanceFunctionMatcher {
   
   private final FunctionResolver resolver;
   private final TypeExtractor extractor;
   
   public TypeInstanceFunctionMatcher(TypeExtractor extractor, FunctionResolver resolver) {
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