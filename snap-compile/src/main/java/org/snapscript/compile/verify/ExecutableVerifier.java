package org.snapscript.compile.verify;
import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceErrorCollector;

public class ExecutableVerifier extends TraceErrorCollector implements Verifier {
   
   private final List<VerifyError> errors;
   
   public ExecutableVerifier() {
      this.errors = new ArrayList<VerifyError>();
   }

   @Override
   public void compileError(Exception cause, Trace trace) {
      VerifyError error = new VerifyError(cause, trace);
      errors.add(error);
   }
   
   public void verify(){
      if(!errors.isEmpty()) {
         throw new VerifyException("Compilation errors found " + errors, errors);
      }
   }
}
