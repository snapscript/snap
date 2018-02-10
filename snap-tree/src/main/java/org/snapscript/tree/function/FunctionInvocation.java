package org.snapscript.tree.function;

import java.util.concurrent.atomic.AtomicInteger;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Index;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Path;
import org.snapscript.core.Scope;
import org.snapscript.core.Table;
import org.snapscript.core.Value;
import org.snapscript.core.dispatch.CallDispatcher;
import org.snapscript.core.function.Function;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceEvaluation;
import org.snapscript.core.trace.TraceInterceptor;
import org.snapscript.tree.ArgumentList;
import org.snapscript.tree.CallSite;
import org.snapscript.tree.NameReference;

public class FunctionInvocation implements Compilation {
   
   private final Evaluation invocation;
   
   public FunctionInvocation(Evaluation function, ArgumentList arguments, Evaluation... evaluations) {
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
   
      private final NameReference reference;
      private final ArgumentList arguments;
      private final Evaluation[] evaluations; // func()[1][x]
      private final AtomicInteger offset;
      private final CallSite site;
      
      public CompileResult(Evaluation function, ArgumentList arguments, Evaluation... evaluations) {
         this.reference = new NameReference(function);
         this.site = new CallSite(reference);
         this.offset = new AtomicInteger();
         this.evaluations = evaluations;
         this.arguments = arguments;
      }
      
      @Override
      public void compile(Scope scope) throws Exception {
         String name = reference.getName(scope); 
         Index index = scope.getIndex();
         int depth = index.get(name);
         
         offset.set(depth);
         arguments.compile(scope);
         
         for(Evaluation evaluation : evaluations) {
            evaluation.compile(scope);
         }
      }
      
      @Override
      public Value validate(Scope scope, Object left) throws Exception {
         int depth = offset.get();
         
         if(depth != -1 && left == null){
            Table table = scope.getTable();
            Value value = table.get(depth);
            
            if(value != null) {
               Object object = value.getValue();
            
               if(Function.class.isInstance(object)) {
                  return executeV(scope, value);
               }
            }
         }
         return executeV(scope, left);
      }
      
      private Value executeV(Scope scope, Object left) throws Exception {
         String name = reference.getName(scope); 
         Value array = arguments.create(scope); 
         Object[] arguments = array.getValue();
         CallDispatcher handler = site.get(scope, left);
         Value value = handler.validate(scope, left, arguments);
         
         for(Evaluation evaluation : evaluations) {
            Object result = value.getValue();
            
            if(result == null) {
               throw new InternalStateException("Result of '" + name + "' null"); 
            }
            value = evaluation.validate(scope, result);
         }
         return value; 
      }

      @Override
      public Value evaluate(Scope scope, Object left) throws Exception {
         int depth = offset.get();
         
         if(depth != -1 && left == null){
            Table table = scope.getTable();
            Value value = table.get(depth);
            
            if(value != null) {
               Object object = value.getValue();
            
               if(Function.class.isInstance(object)) {
                  return execute(scope, value);
               }
            }
         }
         return execute(scope, left);
      }
      
      private Value execute(Scope scope, Object left) throws Exception {
         String name = reference.getName(scope); 
         Value array = arguments.create(scope); 
         Object[] arguments = array.getValue();
         CallDispatcher handler = site.get(scope, left);
         Value value = handler.dispatch(scope, left, arguments);
         
         for(Evaluation evaluation : evaluations) {
            Object result = value.getValue();
            
            if(result == null) {
               throw new InternalStateException("Result of '" + name + "' null"); 
            }
            value = evaluation.evaluate(scope, result);
         }
         return value; 
      }
   }
}