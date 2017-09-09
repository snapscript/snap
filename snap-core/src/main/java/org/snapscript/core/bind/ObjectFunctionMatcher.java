package org.snapscript.core.bind;

import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.bind2.FunctionResolver2;
import org.snapscript.core.function.Function;
import org.snapscript.core.stack.ThreadStack;

public class ObjectFunctionMatcher {
   
   private final FunctionResolver resolver;
   private final TypeExtractor extractor;
   private final ThreadStack stack;
   
   private final FunctionResolver2 resolver2;
   
   public ObjectFunctionMatcher(TypeExtractor extractor, ThreadStack stack, FunctionResolver resolver, FunctionResolver2 resolver2) {
      this.extractor = extractor;
      this.resolver = resolver;
      this.resolver2 = resolver2;
      this.stack = stack;
   }

   public FunctionPointer match(Object value, String name, Object... values) throws Exception { 
      Type type = extractor.getType(value);
      Function function = resolver2.resolve(type, name, values);
      
      if(function != null) {
         return new FunctionPointer(function, stack, values);
      }
      return null;
   }
}