package org.snapscript.core.error;

import org.snapscript.core.Module;
import org.snapscript.core.Type;
import org.snapscript.core.function.Function;
import org.snapscript.core.trace.Trace;

public class StackElement {
   
   private static final String MAIN_FUNCTION = "main";
   
   private final Function function;
   private final Trace trace;
   
   public StackElement(Trace trace) {
      this(trace, null);
   }
   
   public StackElement(Trace trace, Function function) {
      this.function = function;
      this.trace = trace;
   }
   
   public StackTraceElement build() {
      Module module = trace.getModule();
      String path = module.getPath();
      String name = module.getName();
      int line = trace.getLine();
      
      return create(path, name, line);
   }
   
   private StackTraceElement create(String path, String module, int line) {
      if(function != null) {
         String name = function.getName();
         Type type = function.getType();
         
         if(type != null) {
            Module parent = type.getModule();
            String prefix = parent.getName();
            String suffix = type.getName();
            
            return new StackTraceElement(prefix + "." + suffix, name, path, line);
         }
         return new StackTraceElement(module, name, path, line);
      }
      return new StackTraceElement(module, MAIN_FUNCTION, path, line);
   }
}