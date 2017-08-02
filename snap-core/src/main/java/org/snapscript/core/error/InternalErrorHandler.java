package org.snapscript.core.error;

import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.stack.ThreadStack;

public class InternalErrorHandler {

   private final InternalErrorBuilder builder;
   
   public InternalErrorHandler(ThreadStack stack) {
      this(stack, true);
   }
   
   public InternalErrorHandler(ThreadStack stack, boolean replace) {
      this.builder = new InternalErrorBuilder(stack, replace);
   }
   
   public Result throwInternal(Scope scope, Object value) {
      throw builder.create(value);
   }
}