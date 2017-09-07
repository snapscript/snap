package org.snapscript.core.error;

import org.snapscript.core.Path;
import org.snapscript.core.trace.Trace;

public class InternalErrorFormatter {
   
   public InternalErrorFormatter() {
      super();
   }

   public String format(Throwable cause, Trace trace) {
      String message = cause.getMessage();
      Path path = trace.getPath();
      int line = trace.getLine();
      
      return String.format("%s in %s at line %s", message, path, line);
   }
}
