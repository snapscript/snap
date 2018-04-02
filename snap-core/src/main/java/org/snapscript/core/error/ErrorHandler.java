package org.snapscript.core.error;

import org.snapscript.core.type.Type;
import org.snapscript.core.module.Module;
import org.snapscript.core.result.Result;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.stack.ThreadStack;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.type.TypeExtractor;

public class ErrorHandler {

   private final ExternalErrorHandler external;
   private final InternalErrorHandler internal;
   
   public ErrorHandler(TypeExtractor extractor, ThreadStack stack) {
      this(extractor, stack, true);
   }
   
   public ErrorHandler(TypeExtractor extractor, ThreadStack stack, boolean replace) {
      this.internal = new InternalErrorHandler(extractor, stack, replace);
      this.external = new ExternalErrorHandler();
   }
   
   public Result handleCompileError(Scope scope, String name) {
      return internal.handleCompileError(scope, name); 
   }

   public Result handleCompileError(Scope scope, Type type, String name) {
      return internal.handleCompileError(scope, type, name); 
   }
   
   public Result handleCompileError(Scope scope, String name, Type[] list) {
      return internal.handleCompileError(scope, name, list); 
   }

   public Result handleCompileError(Scope scope, Type type, String name, Type[] list) {
      return internal.handleCompileError(scope, type, name, list); 
   }

   public Result handleRuntimeError(Scope scope, String name) {
      return internal.handleRuntimeError(scope, name); 
   }

   public Result handleRuntimeError(Scope scope, Object object, String name) {
      return internal.handleRuntimeError(scope, object, name); 
   }
   
   public Result handleRuntimeError(Scope scope,String name, Object[] list) {
      return internal.handleRuntimeError(scope, name, list); 
   }
   
   public Result handleRuntimeError(Scope scope, Object object, String name, Object[] list) {
      return internal.handleRuntimeError(scope, object, name, list); 
   }
   
   public Result handleRuntimeError(Scope scope, Type type, String name, Object[] list) {
      return internal.handleRuntimeError(scope, type, name, list); 
   }
   
   public Result handleRuntimeError(Scope scope, Module module, String name, Object[] list) {
      return internal.handleRuntimeError(scope, module, name, list); 
   }
   
   public Result handleInternalError(Scope scope, Object cause) {
      if(InternalError.class.isInstance(cause)) {
         throw (InternalError)cause;
      }
      return internal.handleInternalError(scope, cause); // fill in trace
   }
   
   public Result handleInternalError(Scope scope, Throwable cause, Trace trace) {
      if(InternalError.class.isInstance(cause)) {
         throw (InternalError)cause;
      }
      return internal.handleInternalError(scope, cause, trace); 
   }
   
   public Result handleExternalError(Scope scope, Throwable cause) throws Exception {
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