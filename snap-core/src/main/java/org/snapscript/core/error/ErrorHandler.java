package org.snapscript.core.error;

import org.snapscript.core.module.Module;
import org.snapscript.core.result.Result;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.stack.ThreadStack;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeExtractor;

public class ErrorHandler {

   private final ExternalErrorHandler external;
   private final InternalErrorHandler internal;
   private final RuntimeErrorHandler runtime;
   private final CompileErrorHandler compile;
   
   public ErrorHandler(TypeExtractor extractor, ThreadStack stack) {
      this(extractor, stack, true);
   }
   
   public ErrorHandler(TypeExtractor extractor, ThreadStack stack, boolean replace) {
      this.internal = new InternalErrorHandler(extractor, stack, replace);
      this.compile = new CompileErrorHandler(extractor, stack, replace);
      this.runtime = new RuntimeErrorHandler(extractor, stack, replace);
      this.external = new ExternalErrorHandler();
   }
   
   public Result handleCompileError(Reason reason, Scope scope, String name) {
      if(reason.isAccess()) {
         return compile.handleAccessError(scope, name);          
      }
      return compile.handleReferenceError(scope, name); 
   }

   public Result handleCompileError(Reason reason, Scope scope, Type type, String name) {
      if(reason.isAccess()) {
         return compile.handleAccessError(scope, type, name);          
      }
      return compile.handleReferenceError(scope, type, name); 
   }
   
   public Result handleCompileError(Reason reason, Scope scope, Type require, Type actual) {
      return compile.handleCastError(scope, require, actual); 
   }
   
   public Result handleCompileError(Reason reason, Scope scope, String name, Type[] list) {
      if(reason.isAccess()) {
         return compile.handleAccessError(scope, name, list);          
      }
      return compile.handleInvokeError(scope, name, list); 
   }

   public Result handleCompileError(Reason reason, Scope scope, Type type, String name, Type[] list) {
      if(reason.isAccess()) {
         return compile.handleAccessError(scope, type, name, list);          
      }
      return compile.handleInvokeError(scope, type, name, list); 
   }

   public Result handleRuntimeError(Reason reason, Scope scope, String name) {
      return runtime.handleReferenceError(scope, name); 
   }

   public Result handleRuntimeError(Reason reason, Scope scope, Object object, String name) {
      return runtime.handleReferenceError(scope, object, name); 
   }
   
   public Result handleRuntimeError(Reason reason, Scope scope, String name, Object[] list) {
      return runtime.handleInvokeError(scope, name, list); 
   }
   
   public Result handleRuntimeError(Reason reason, Scope scope, Object object, String name, Object[] list) {
      return runtime.handleInvokeError(scope, object, name, list); 
   }
   
   public Result handleRuntimeError(Reason reason, Scope scope, Type type, String name, Object[] list) {
      return runtime.handleInvokeError(scope, type, name, list); 
   }
   
   public Result handleRuntimeError(Reason reason, Scope scope, Module module, String name, Object[] list) {
      return runtime.handleInvokeError(scope, module, name, list); 
   }
   
   public Result handleInternalError(Reason reason, Scope scope, Object cause) {
      if(InternalError.class.isInstance(cause)) {
         throw (InternalError)cause;
      }
      return internal.handleError(scope, cause); // fill in trace
   }
   
   public Result handleInternalError(Reason reason, Scope scope, Throwable cause, Trace trace) {
      if(InternalError.class.isInstance(cause)) {
         throw (InternalError)cause;
      }
      return internal.handleError(scope, cause, trace); 
   }
   
   public Result handleExternalError(Reason reason, Scope scope, Throwable cause) throws Exception {
      if(InternalError.class.isInstance(cause)) {
         InternalError error = (InternalError)cause;
         Object original = error.getValue();
         
         if(Exception.class.isInstance(original)) {
            throw (Exception)original; // throw original value
         }
         return external.handleError(scope, cause); // no stack trace
      }
      return external.handleError(scope, cause); // no stack trace
   }
}