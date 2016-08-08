package org.snapscript.core.error;

import org.snapscript.core.Result;
import org.snapscript.core.Scope;

public class ErrorHandler {

   private final ExternalErrorHandler external;
   private final InternalErrorHandler internal;
   
   public ErrorHandler(ThreadStack stack) {
      this(stack, true);
   }
   
   public ErrorHandler(ThreadStack stack, boolean replace) {
      this.internal = new InternalErrorHandler(stack, replace);
      this.external = new ExternalErrorHandler();
   }
   
   public Result throwInternal(Scope scope, Object cause) {
      if(InternalError.class.isInstance(cause)) {
         throw (InternalError)cause;
      }
      return internal.throwInternal(scope, cause); // fill in trace
   }
   
   public Result throwExternal(Scope scope, Throwable cause) throws Exception {
      if(InternalError.class.isInstance(cause)) {
         InternalError error = (InternalError)cause;
         Object original = error.getValue();
         
         if(Exception.class.isInstance(original)) {
            throw (Exception)original; // throw original
         }
         return external.throwExternal(scope, original); // no stack trace
      }
      return external.throwExternal(scope, cause); // no stack trace
   }
}
