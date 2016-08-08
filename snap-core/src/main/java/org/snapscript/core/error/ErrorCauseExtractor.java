package org.snapscript.core.error;

import org.snapscript.core.Scope;

public class ErrorCauseExtractor {

   public ErrorCauseExtractor() {
      super();
   }
   
   public Object extract(Scope scope, Object cause) {
      if(InternalError.class.isInstance(cause)) {
         InternalError error = (InternalError)cause;
         return error.getValue();
      }
      return cause;
   }
}
