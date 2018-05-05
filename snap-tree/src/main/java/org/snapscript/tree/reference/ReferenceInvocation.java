package org.snapscript.tree.reference;

import static org.snapscript.core.error.Reason.ACCESS;

import java.util.concurrent.atomic.AtomicInteger;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.bind.FunctionBinder;
import org.snapscript.core.function.bind.FunctionMatcher;
import org.snapscript.core.function.dispatch.FunctionDispatcher;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.index.Index;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceEvaluation;
import org.snapscript.core.trace.TraceInterceptor;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;
import org.snapscript.tree.ModifierAccessVerifier;
import org.snapscript.tree.ArgumentList;
import org.snapscript.tree.NameReference;

public class ReferenceInvocation implements Compilation {

   private final Evaluation[] evaluations;
   private final NameReference reference;
   private final ArgumentList arguments;
   
   public ReferenceInvocation(Evaluation function, ArgumentList arguments, Evaluation... evaluations) {
      this.reference = new NameReference(function);
      this.evaluations = evaluations;
      this.arguments = arguments;
   }
   
   @Override
   public Evaluation compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      TraceInterceptor interceptor = context.getInterceptor();     
      Trace trace = Trace.getInvoke(module, path, line);
      Evaluation invocation = create(module, path, line);
      
      return new TraceEvaluation(interceptor, invocation, trace);
   }
   
   private Evaluation create(Module module, Path path, int line) throws Exception {
      Scope scope = module.getScope();
      Context context = module.getContext();
      String name = reference.getName(scope);
      FunctionBinder binder = context.getBinder();   
      FunctionMatcher matcher = binder.bind(name);
      
      return new CompileResult(matcher, arguments, evaluations, name);     
   }
   
   private static class CompileResult extends Evaluation {
   
      private final ModifierAccessVerifier verifier;
      private final Evaluation[] evaluations; // func()[1][x]
      private final FunctionMatcher matcher;
      private final ArgumentList arguments;
      private final AtomicInteger offset;
      private final String name;
      
      public CompileResult(FunctionMatcher matcher, ArgumentList arguments, Evaluation[] evaluations, String name) {
         this.verifier = new ModifierAccessVerifier();
         this.offset = new AtomicInteger();
         this.evaluations = evaluations;
         this.arguments = arguments;
         this.matcher = matcher;
         this.name = name;
      }
      
      @Override
      public void define(Scope scope) throws Exception { 
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
         Type type = left.getType(scope);         
         Type[] array = arguments.compile(scope); 
         FunctionDispatcher dispatcher = matcher.match(scope, left);
         Constraint result = dispatcher.compile(scope, left, array);

         if(result.isPrivate()) {
            Module module = scope.getModule();
            Context context = module.getContext();
            ErrorHandler handler = context.getHandler();
            
            if(!verifier.isAccessible(scope, type)) {
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
         Object[] array = arguments.create(scope); 
         FunctionDispatcher dispatcher = matcher.match(scope, left);
         Value value = dispatcher.dispatch(scope, left, array);
         
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