package org.snapscript.core.error;

import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.stack.ThreadStack;
import org.snapscript.core.trace.Trace;

public class ErrorHandler {

   private final ExternalErrorHandler external;
   private final InternalErrorHandler internal;
   
   public ErrorHandler(TypeExtractor extractor, ThreadStack stack) {
      this(extractor, stack, false);
   }
   
   public ErrorHandler(TypeExtractor extractor, ThreadStack stack, boolean replace) {
      this.internal = new InternalErrorHandler(extractor, stack, replace);
      this.external = new ExternalErrorHandler();
   }
   
   public Result throwInternalException(Scope scope,String name, Object... list) {
      return internal.throwInternalException(scope, name, list); 
   }
   
   public Result throwInternalException(Scope scope, Object object, String name, Object... list) {
      return internal.throwInternalException(scope, object, name, list); 
   }
   
   public Result throwInternalException(Scope scope, Type type, String name, Object... list) {
      return internal.throwInternalException(scope, type, name, list); 
   }

   public Result throwInternalException(Scope scope, Type type, String name, Type... list) {
      return internal.throwInternalException(scope, type, name, list); 
   }
   
   public Result throwInternalException(Scope scope, Module module, String name, Object... list) {
      return internal.throwInternalException(scope, module, name, list); 
   }
   
   public Result throwInternalError(Scope scope, Object cause) {
      if(InternalError.class.isInstance(cause)) {
         throw (InternalError)cause;
      }
      return internal.throwInternalError(scope, cause); // fill in trace
   }
   
   public Result throwInternalError(Scope scope, Throwable cause, Trace trace) {
      if(InternalError.class.isInstance(cause)) {
         throw (InternalError)cause;
      }
      return internal.throwInternalError(scope, cause, trace); 
   }
   
   public Result throwExternalError(Scope scope, Throwable cause) throws Exception {
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