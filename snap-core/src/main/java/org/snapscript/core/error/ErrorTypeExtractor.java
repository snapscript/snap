package org.snapscript.core.error;

import org.snapscript.core.Context;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeExtractor;

public class ErrorTypeExtractor {
   
   public ErrorTypeExtractor() {
      super();
   }
   
   public Type extract(Scope scope, Object cause) {
      try {
         Module module = scope.getModule();
         Context context = module.getContext();
         TypeExtractor extractor = context.getExtractor();   
         
         if(InternalError.class.isInstance(cause)) {
            InternalError error = (InternalError)cause;
            Object value = error.getValue();
            
            return extractor.getType(value);
         }
         return extractor.getType(cause);
      } catch(Exception e) {
         return null;
      }
   }
}