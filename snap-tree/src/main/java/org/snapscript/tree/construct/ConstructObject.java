package org.snapscript.tree.construct;

import static org.snapscript.core.ModifierType.ABSTRACT;
import static org.snapscript.core.ModifierType.ENUM;
import static org.snapscript.core.ModifierType.MODULE;
import static org.snapscript.core.ModifierType.TRAIT;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.resolve.FunctionResolver;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceEvaluation;
import org.snapscript.core.trace.TraceInterceptor;
import org.snapscript.tree.ArgumentList;

public class ConstructObject implements Compilation {
   
   private final ArgumentList arguments;
   private final Constraint type;
   
   public ConstructObject(Constraint type) {
      this(type, null);         
   }
   
   public ConstructObject(Constraint type, ArgumentList arguments) {
      this.arguments = arguments;
      this.type = type;
   } 
   
   @Override
   public Evaluation compile(Module module, Path path, int line) throws Exception {
      Evaluation construct = create(module, path, line);
      Context context = module.getContext();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getConstruct(module, path, line);
      
      return new TraceEvaluation(interceptor, construct, trace);
   }
   
   private Evaluation create(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      FunctionResolver resolver = context.getResolver();
      
      return new CreateObject(resolver, handler, type, arguments, ABSTRACT.mask | TRAIT.mask | ENUM.mask | MODULE.mask);
   }
}