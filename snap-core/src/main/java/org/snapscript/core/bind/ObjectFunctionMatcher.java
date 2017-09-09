package org.snapscript.core.bind;

import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.function.Function;
import org.snapscript.core.stack.ThreadStack;

public class ObjectFunctionMatcher {
   
   private final FunctionResolver resolver;
   private final TypeExtractor extractor;
   private final ThreadStack stack;
   
   public ObjectFunctionMatcher(TypeExtractor extractor, ThreadStack stack, FunctionResolver resolver) {
      this.extractor = extractor;
      this.resolver = resolver;
      this.stack = stack;
   }

   public FunctionPointer match(Object value, String name, Object... values) throws Exception { 
      Type type = extractor.getType(value);
      Function function = resolver.resolve(type, name, values);
      
      if(function != null) {
         return new FunctionPointer(function, stack, values);
      }
      return null;
   }
}