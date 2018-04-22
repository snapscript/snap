package org.snapscript.tree.construct;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceEvaluation;
import org.snapscript.core.trace.TraceInterceptor;
import org.snapscript.tree.ArgumentList;

public class ConstructObject implements Compilation {
   
   private final Evaluation construct;
   
   public ConstructObject(Constraint type) {
      this(type, null);         
   }
   
   public ConstructObject(Constraint type, ArgumentList arguments) {
      this.construct = new CreateObject(type, arguments);
   } 
   
   @Override
   public Evaluation compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getConstruct(module, path, line);
      
      return new TraceEvaluation(interceptor, construct, trace);
   }

}