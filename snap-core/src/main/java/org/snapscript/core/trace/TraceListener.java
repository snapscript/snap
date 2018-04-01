package org.snapscript.core.trace;

import org.snapscript.core.scope.Scope;
                   
public interface TraceListener {
   void traceBefore(Scope scope, Trace trace);
   void traceAfter(Scope scope, Trace trace);
   void traceCompileError(Scope scope, Trace trace, Exception cause);
   void traceRuntimeError(Scope scope, Trace trace, Exception cause);

}