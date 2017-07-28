package org.snapscript.core.generate;

import java.lang.reflect.Constructor;

import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.bind.FunctionResolver;
import org.snapscript.core.bind.ObjectFunctionMatcher;
import org.snapscript.core.stack.ThreadStack;

public class TypeExtenderBuilder {
   
   private final TypeExtenderLoader loader;
   private final FunctionResolver resolver;
   
   public TypeExtenderBuilder(TypeExtractor extractor, ThreadStack stack) {
      this.resolver = new ObjectFunctionMatcher(extractor, stack);
      this.loader = new TypeExtenderLoader();
   }

   public TypeExtender create(Type type) {
      if(type != null) {
         try {
            Constructor constructor = loader.load();
            Object extender = constructor.newInstance(resolver, type);
            
            return (TypeExtender)extender;
         }catch(Exception e) {
            throw new IllegalStateException("Could not extend " + type, e);
         }
      }
      return null;
   }
}