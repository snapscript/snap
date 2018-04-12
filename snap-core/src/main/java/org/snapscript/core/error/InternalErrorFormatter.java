package org.snapscript.core.error;

import org.snapscript.core.module.Path;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.type.TypeExtractor;

public class InternalErrorFormatter {
   
   private final TypeExtractor extractor;
   
   public InternalErrorFormatter(TypeExtractor extractor) {
      this.extractor = extractor;
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