package org.snapscript.core.trace;

import org.snapscript.core.Module;
import org.snapscript.core.Path;

public enum TraceType {
   CONSTRUCT,
   INVOKE,
   NORMAL,
   NATIVE;
   
   public static Trace getNative(Module module, Path path) {
      return new Trace(NATIVE, module, path, -2); // see StackTraceElement.isNativeMethod
   }
   
   public static Trace getConstruct(Module module, Path path, int line) {
      return new Trace(CONSTRUCT, module, path, line);
   }
   
   public static Trace getInvoke(Module module, Path path, int line) {
      return new Trace(INVOKE, module, path, line);
   }
   
   public static Trace getNormal(Module module, Path path, int line) {
      return new Trace(NORMAL, module, path, line);
   }
}
