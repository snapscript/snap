package org.snapscript.core.module;

import org.snapscript.core.InternalArgumentException;

public class ModuleException extends InternalArgumentException {

   public ModuleException(String message) {
      super(message);
   }
   
   public ModuleException(String message, Throwable cause) {
      super(message, cause);
   }
}