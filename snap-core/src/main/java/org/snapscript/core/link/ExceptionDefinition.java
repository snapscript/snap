package org.snapscript.core.link;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Statement;
import org.snapscript.core.module.Path;
import org.snapscript.core.scope.Scope;

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