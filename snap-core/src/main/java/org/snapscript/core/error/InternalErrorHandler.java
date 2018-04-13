package org.snapscript.core.error;

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
      this.formatter = new InternalErrorFormatter();
   }
   
   public Result handleError(Scope scope, Object value) {
      throw builder.createInternalError(value);
   }
   
   public Result handleError(Scope scope, Throwable cause, Trace trace) {
      String message = formatter.formatInternalError(cause, trace);
      throw builder.createInternalError(message);
   }
}