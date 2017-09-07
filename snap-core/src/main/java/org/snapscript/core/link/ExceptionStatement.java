package org.snapscript.core.link;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;

public class ExceptionStatement extends Statement {
   
   private final Exception cause;
   private final String message;
   
   public ExceptionStatement(String message, Exception cause) {
      this.message = message;
      this.cause = cause;
   }
   
   @Override
   public Result define(Scope scope) throws Exception {
      throw new InternalStateException(message, cause);
   }
                  
   @Override
   public Result compile(Scope scope) throws Exception {
      throw new InternalStateException(message, cause);
   }
   
   @Override
   public Result execute(Scope scope) throws Exception {
      throw new InternalStateException(message, cause);
   }
}