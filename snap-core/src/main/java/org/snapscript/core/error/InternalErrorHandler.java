package org.snapscript.core.error;

import static org.snapscript.core.result.Result.NORMAL;

import org.snapscript.core.type.Type;
import org.snapscript.core.module.Module;
import org.snapscript.core.result.Result;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.stack.ThreadStack;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.type.TypeExtractor;

public class InternalErrorHandler {

   private final InternalErrorFormatter formatter;
   private final InternalErrorBuilder builder;
   
   public InternalErrorHandler(TypeExtractor extractor, ThreadStack stack) {
      this(extractor, stack, true);
   }
   
   public InternalErrorHandler(TypeExtractor extractor, ThreadStack stack, boolean replace) {
      this.builder = new InternalErrorBuilder(stack, replace);
      this.formatter = new InternalErrorFormatter(extractor);
   }
   
   public Result handleInternalError(Scope scope, Object value) {
      throw builder.createInternalError(value);
   }
   
   public Result handleInternalError(Scope scope, Throwable cause, Trace trace) {
      String message = formatter.formatInternalError(cause, trace);
      throw builder.createInternalError(message);
   }

   public Result handleCompileError(Scope scope, String name) {
      String message = formatter.formatCompileError(name);
      throw builder.createCompileException(message);
   }
   
   public Result handleCompileError(Scope scope, Type type, String name) {
      String message = formatter.formatCompileError(type, name);
      throw builder.createCompileException(message);
   }
   
   public Result handleCompileError(Scope scope, String name, Type[] list) {
      String message = formatter.formatCompileError(name, list);
      throw builder.createCompileException(message);
   }
   
   public Result handleCompileError(Scope scope, Type type, String name, Type[] list) {
      String message = formatter.formatCompileError(type, name, list);
      throw builder.createCompileException(message);
   }
   
   public Result handleRuntimeError(Scope scope, String name) {
      String message = formatter.formatRuntimeError(name);
      throw builder.createRuntimeException(message);
   }
   
   public Result handleRuntimeError(Scope scope, Object object, String name) {
      String message = formatter.formatRuntimeError(object, name);
      throw builder.createRuntimeException(message);
   }
   
   public Result handleRuntimeError(Scope scope, String name, Object[] list) {
      String message = formatter.formatRuntimeError(name, list);
      throw builder.createRuntimeException(message);
   }
   
   public Result handleRuntimeError(Scope scope, Object value, String name, Object[] list) {
      String message = formatter.formatRuntimeError(value, name, list);
      throw builder.createRuntimeException(message);
   }
   
   public Result handleRuntimeError(Scope scope, Type type, String name, Object[] list) {
      String message = formatter.formatRuntimeError(type, name, list);
      throw builder.createRuntimeException(message);
   }
   
   public Result handleRuntimeError(Scope scope, Module module, String name, Object[] list) {
      String message = formatter.formatRuntimeError(module, name, list);
      throw builder.createRuntimeException(message);
   }
}