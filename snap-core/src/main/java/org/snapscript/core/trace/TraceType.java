package org.snapscript.core.trace;

import org.snapscript.core.Module;

public enum TraceType {
   CONSTRUCT,
   SCOPE,
   INVOKE,
   NORMAL,
   NATIVE;
   
   public static Trace getNative(Module module) {
      return new Trace(NATIVE, module, -2); // see StackTraceElement.isNativeMethod
   }
   
   public static Trace getConstruct(Module module, int line) {
      return new Trace(CONSTRUCT, module, line);
   }
   
   public static Trace getInvoke(Module module, int line) {
      return new Trace(INVOKE, module, line);
   }
   
   public static Trace getNormal(Module module, int line) {
      return new Trace(NORMAL, module, line);
   }
   
   public static Trace getScope(Module module, int line) {
      return new Trace(SCOPE, module, line);
   }
}
