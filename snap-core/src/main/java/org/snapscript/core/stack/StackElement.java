package org.snapscript.core.stack;

import org.snapscript.core.function.Function;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.type.NameBuilder;
import org.snapscript.core.type.Type;

public class StackElement {
   
   private static final String MAIN_FUNCTION = "main";
   
   private final NameBuilder builder;
   private final Function function;
   private final Trace trace;
   
   public StackElement(Trace trace) {
      this(trace, null);
   }
   
   public StackElement(Trace trace, Function function) {
      this.builder = new NameBuilder();
      this.function = function;
      this.trace = trace;
   }
   
   public StackTraceElement build() {
      Module module = trace.getModule();
      String name = module.getName();
      Path path = trace.getPath();
      int line = trace.getLine();
      
      return create(name, path, line);
   }
   
   private StackTraceElement create(String module, Path path, int line) {
      String resource = path.getPath();
      
      if(function != null) {
         String name = function.getName();
         Type type = function.getType();
         
         if(type != null) {
            Module parent = type.getModule();
            String prefix = parent.getName();
            String suffix = type.getName(); // module functions have no type name
            String qualifier = builder.createFullName(prefix, suffix);
            
            return new StackTraceElement(qualifier, name, resource, line);
         }
         return new StackTraceElement(module, name, resource, line);
      }
      return new StackTraceElement(module, MAIN_FUNCTION, resource, line);
   }
}