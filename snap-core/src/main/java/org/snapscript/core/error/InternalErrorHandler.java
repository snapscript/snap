package org.snapscript.core.error;

import org.snapscript.core.Result;
import org.snapscript.core.Scope;

public class InternalErrorHandler {

   private final ThreadStack stack;
   private final boolean replace;
   
   public InternalErrorHandler(ThreadStack stack) {
      this(stack, true);
   }
   
   public InternalErrorHandler(ThreadStack stack, boolean replace) {
      this.replace = replace;
      this.stack = stack;
   }
   
   public Result throwInternal(Scope scope, Object value) {
      InternalError error = new InternalError(value);
      
      if(replace) {
         if(Throwable.class.isInstance(value)) {
            Throwable cause = (Throwable)value;
            StackTraceElement[] trace = stack.build(cause);
            
            if(trace.length > 0) {
               cause.setStackTrace(trace);
               error.setStackTrace(trace);
            }
         } else {
            StackTraceElement[] trace = stack.build();
            
            if(trace.length > 0) {
               error.setStackTrace(trace); // when there is no cause
            }
         }
      }
      throw error;
   }
}
