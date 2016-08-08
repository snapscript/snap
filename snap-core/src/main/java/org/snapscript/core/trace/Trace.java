package org.snapscript.core.trace;

import org.snapscript.core.Module;

public class Trace {
   
   private final TraceType type;
   private final Module module;
   private final int line;
   
   public Trace(TraceType type, Module module, int line) {
      this.module = module;
      this.line = line;
      this.type = type;
   }

   public TraceType getType() {
      return type;
   }

   public Module getModule() {
      return module;
   }

   public int getLine() {
      return line;
   }
   
   @Override
   public String toString() {
      return String.format("%s:%s", module, line);
   }
}
