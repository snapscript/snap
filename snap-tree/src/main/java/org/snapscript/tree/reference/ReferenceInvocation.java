package org.snapscript.tree.reference;

import static org.snapscript.core.error.Reason.ACCESS;

import java.util.concurrent.atomic.AtomicInteger;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.error.Reason;
import org.snapscript.core.function.dispatch.FunctionDispatcher;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.Value;
import org.snapscript.core.scope.index.Index;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceEvaluation;
import org.snapscript.core.trace.TraceInterceptor;
import org.snapscript.core.type.Type;
import org.snapscript.tree.AccessChecker;
import org.snapscript.tree.ArgumentList;
import org.snapscript.tree.NameReference;
import org.snapscript.tree.function.FunctionHolder;

public class ReferenceInvocation implements Compilation {
   
   private final Evaluation invocation;
   
   public ReferenceInvocation(Evaluation function, ArgumentList arguments, Evaluation... evaluations) {
      this.invocation = new CompileResult(function, arguments, evaluations);
   }
   
   @Override
   public Evaluation compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getInvoke(module, path, line);
      
      return new TraceEvaluation(interceptor, invocation, trace);
   }
   
   private static class CompileResult extends Evaluation {
   
      private final Evaluation[] evaluations; // func()[1][x]
      private final NameReference reference;
      private final ArgumentList arguments;
      private final FunctionHolder holder;
      private final AccessChecker checker;
      private final AtomicInteger offset;
      
      public CompileResult(Evaluation function, ArgumentList arguments, Evaluation... evaluations) {
         this.reference = new NameReference(function);
         this.holder = new FunctionHolder(reference);
         this.checker = new AccessChecker();
         this.offset = new AtomicInteger();
         this.evaluations = evaluations;
         this.arguments = arguments;
      }
      
      @Override
      public void define(Scope scope) throws Exception {
         String name = reference.getName(scope); 
         Index index = scope.getIndex();
         int depth = index.get(name);

         offset.set(depth);
         arguments.define(scope);
         
         for(Evaluation evaluation : evaluations) {
            evaluation.define(scope);
         }
      }
      
      @Override
      public Constraint compile(Scope scope, Constraint left) throws Exception {
         String name = reference.getName(scope); 
         Type type = left.getType(scope);         
         Type[] array = arguments.compile(scope); 
         FunctionDispatcher dispatcher = holder.get(scope, left);
         Constraint result = dispatcher.compile(scope, type, array);
         
         if(result.isPrivate()) {
            Module module = scope.getModule();
            Context context = module.getContext();
            ErrorHandler handler = context.getHandler();
            
            if(!checker.isAccessible(scope, type)) {
               handler.handleCompileError(ACCESS, scope, type, name, array);
            }
         }
         for(Evaluation evaluation : evaluations) {
            if(result == null) {
               throw new InternalStateException("Result of '" + name + "' is null"); 
            }
            result = evaluation.compile(scope, result);
         }
         return result; 
      }

      @Override
      public Value evaluate(Scope scope, Object left) throws Exception {
         String name = reference.getName(scope); 
         Object[] array = arguments.create(scope); 
         FunctionDispatcher handler = holder.get(scope, left);
         Value value = handler.evaluate(scope, left, array);
         
         for(Evaluation evaluation : evaluations) {
            Object result = value.getValue();
            
            if(result == null) {
               throw new InternalStateException("Result of '" + name + "' is null"); 
            }
            value = evaluation.evaluate(scope, result);
         }
         return value; 
      }
   }
}