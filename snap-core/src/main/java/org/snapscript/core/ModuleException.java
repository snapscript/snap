package org.snapscript.core;

public class ModuleException extends InternalArgumentException {

   public ModuleException(String message) {
      super(message);
   }
   
   public ModuleException(String message, Throwable cause) {
      super(message, cause);
   }
}
