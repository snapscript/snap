
package org.snapscript.core.trace;

import org.snapscript.core.Scope;
                   
public interface TraceListener {
   void before(Scope scope, Trace trace);
   void error(Scope scope, Trace trace, Exception cause);
   void after(Scope scope, Trace trace);

}
