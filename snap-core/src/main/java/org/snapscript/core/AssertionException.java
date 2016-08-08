package org.snapscript.core;

public class AssertionException extends InternalException {

   public AssertionException(String message) {
      super(message);
   }
   
   public AssertionException(Throwable cause) {
      super(cause);
   }
   
   public AssertionException(String message, Throwable cause) {
      super(message, cause);
   }
}
