package org.snapscript.core.error;

import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.stack.ThreadStack;
import org.snapscript.core.trace.Trace;

public class InternalErrorHandler {

   private final InternalErrorFormatter formatter;
   private final InternalErrorBuilder builder;
   
   public InternalErrorHandler(ThreadStack stack) {
      this(stack, true);
   }
   
   public InternalErrorHandler(ThreadStack stack, boolean replace) {
      this.builder = new InternalErrorBuilder(stack, replace);
      this.formatter = new InternalErrorFormatter();
   }
   
   public Result throwInternal(Scope scope, Object value) {
      throw builder.create(value);
   }
   
   public Result throwInternal(Scope scope, Throwable cause, Trace trace) {
      String message = formatter.format(cause, trace);
      throw builder.create(message);
   }
}