package org.snapscript.core.link;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Path;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;

public class ExceptionDefinition implements PackageDefinition {
   
   private final Exception cause;
   private final String message;
   
   public ExceptionDefinition(String message, Exception cause) {
      this.message = message;
      this.cause = cause;
   }

   @Override
   public Statement define(Scope scope, Path from) throws Exception {
      throw new InternalStateException(message, cause);
   }
}