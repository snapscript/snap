package org.snapscript.core.error;

import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.ValueTypeExtractor;

public class ErrorTypeExtractor {

   private final ValueTypeExtractor extractor;
   
   public ErrorTypeExtractor() {
      this.extractor = new ValueTypeExtractor();
   }
   
   public Type extract(Scope scope, Object cause) {
      try {
         if(InternalError.class.isInstance(cause)) {
            InternalError error = (InternalError)cause;
            Object value = error.getValue();
            
            return extractor.extract(scope, value);
         }
         return extractor.extract(scope, cause);
      } catch(Exception e) {
         return null;
      }
   }
}
