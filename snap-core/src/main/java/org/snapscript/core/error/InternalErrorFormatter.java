package org.snapscript.core.error;

import org.snapscript.core.module.Path;
import org.snapscript.core.trace.Trace;

public class InternalErrorFormatter {
   
   public InternalErrorFormatter() {
      super();
   }

   public String formatInternalError(Throwable cause, Trace trace) {
      StringBuilder builder = new StringBuilder();
      
      if(trace != null) {
         String message = cause.getMessage();
         Path path = trace.getPath();
         int line = trace.getLine();
         
         builder.append(message);
         builder.append(" in ");
         builder.append(path);
         builder.append(" at line ");
         builder.append(line);

         return builder.toString();
      }
      return cause.getMessage();
   }
}