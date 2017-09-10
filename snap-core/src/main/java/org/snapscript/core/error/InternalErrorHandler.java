package org.snapscript.core.error;

import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.stack.ThreadStack;
import org.snapscript.core.trace.Trace;

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
   
   public Result throwInternalError(Scope scope, Object value) {
      throw builder.createError(value);
   }
   
   public Result throwInternalError(Scope scope, Throwable cause, Trace trace) {
      String message = formatter.format(cause, trace);
      throw builder.createError(message);
   }
   
   public Result throwInternalException(Scope scope, String name, Object... list) {
      String message = formatter.format(name, list);
      throw builder.createException(message);
   }
   
   public Result throwInternalException(Scope scope, Object value, String name, Object... list) {
      String message = formatter.format(value, name, list);
      throw builder.createException(message);
   }
   
   public Result throwInternalException(Scope scope, Type type, String name, Object... list) {
      String message = formatter.format(type, name, list);
      throw builder.createException(message);
   }
   
   public Result throwInternalException(Scope scope, Module module, String name, Object... list) {
      String message = formatter.format(module, name, list);
      throw builder.createException(message);
   }
}