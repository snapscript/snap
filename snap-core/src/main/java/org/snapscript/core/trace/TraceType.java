package org.snapscript.core.trace;

import org.snapscript.core.Module;

public enum TraceType {
   CONSTRUCT,
   INVOKE,
   NORMAL;
   
   public static Trace getConstruct(Module module, int line) {
      return new Trace(CONSTRUCT, module, line);
   }
   
   public static Trace getInvoke(Module module, int line) {
      return new Trace(INVOKE, module, line);
   }
   
   public static Trace getNormal(Module module, int line) {
      return new Trace(NORMAL, module, line);
   }
}
