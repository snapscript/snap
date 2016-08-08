package org.snapscript.core.trace;

import org.snapscript.core.Scope;
                   
public interface TraceListener {
   void before(Scope scope, Trace trace);
   void after(Scope scope, Trace trace);
}
