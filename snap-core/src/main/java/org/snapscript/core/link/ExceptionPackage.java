package org.snapscript.core.link;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;

public class ExceptionPackage implements Package {
   
   private final Exception cause;
   private final String message;
   
   public ExceptionPackage(String message, Exception cause) {
      this.message = message;
      this.cause = cause;
   }  
   
   @Override
   public PackageDefinition define(Scope scope) throws Exception {
      throw new InternalStateException(message, cause);
   }             
}