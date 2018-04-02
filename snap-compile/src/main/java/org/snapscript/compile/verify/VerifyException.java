package org.snapscript.compile.verify;

import java.util.List;

import org.snapscript.core.InternalException;

public class VerifyException extends InternalException {
   
   private final List<VerifyError> errors;
   
   public VerifyException(String message, List<VerifyError> errors) {
      super(message);
      this.errors = errors;
   }
   
   public List<VerifyError> getErrors() {
      return errors;
   }
}