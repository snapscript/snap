package org.snapscript.core.stack;

import org.snapscript.core.error.InternalException;

public class StackOverflowException extends InternalException {

   public StackOverflowException(String message) {
      super(message);
   }
   
   public StackOverflowException(Throwable cause) {
      super(cause);
   }
   
   public StackOverflowException(String message, Throwable cause) {
      super(message, cause);
   }
}