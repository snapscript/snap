package org.snapscript.compile.validate;

import org.snapscript.core.InternalException;

public class ValidateException extends InternalException {

   public ValidateException(String message) {
      super(message);
   }
   
   public ValidateException(String message, Throwable cause) {
      super(message, cause);
   }
}
