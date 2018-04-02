package org.snapscript.core.link;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.scope.Scope;

public class ExceptionPackage implements Package {
   
   private final Exception cause;
   private final String message;
   
   public ExceptionPackage(String message, Exception cause) {
      this.message = message;
      this.cause = cause;
   }  
   
   @Override
   public PackageDefinition create(Scope scope) throws Exception {
      throw new InternalStateException(message, cause);
   }             
}