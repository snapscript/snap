package org.snapscript.core.error;

import org.snapscript.core.InternalException;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.stack.ThreadStack;

public class InternalErrorBuilder {

   private final ThreadStack stack;
   private final boolean replace;
   
   public InternalErrorBuilder(ThreadStack stack) {
      this(stack, true);
   }
   
   public InternalErrorBuilder(ThreadStack stack, boolean replace) {
      this.replace = replace;
      this.stack = stack;
   }
   
   public InternalError createInternalError(Object value) {
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
      } else {
         if(Throwable.class.isInstance(value)) {
            Throwable cause = (Throwable)value;
            StackTraceElement[] trace = cause.getStackTrace();
            
            if(trace.length > 0) {
               error.setStackTrace(trace);
            }
         }
      }
      return error;
   }
   
   public InternalException createInternalException(Object value) {
      if(Throwable.class.isInstance(value)) {
         return new InternalStateException((Throwable)value);
      }
      return new InternalStateException(String.valueOf(value));
   }
}