package org.snapscript.compile.verify;

import java.util.List;

public class VerifyException extends RuntimeException {
   
   private final List<VerifyError> errors;
   
   public VerifyException(String message, List<VerifyError> errors) {
      super(message);
      this.errors = errors;
   }
   
   public List<VerifyError> getErrors() {
      return errors;
   }
}