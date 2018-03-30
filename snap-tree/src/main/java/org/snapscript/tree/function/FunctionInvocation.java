package org.snapscript.tree.function;

import java.util.concurrent.atomic.AtomicInteger;

import org.snapscript.core.Bug;
import org.snapscript.core.Compilation;
import org.snapscript.core.Constraint;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Index;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Path;
import org.snapscript.core.Scope;
import org.snapscript.core.Table;
import org.snapscript.core.Type;
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
      
      @Bug("this is vert wrong!!")
      @Override
      public Constraint compile(Scope scope, Constraint left) throws Exception {
         int depth = offset.get();

         if(depth != -1 && left == null){
            Table table = scope.getTable();
            Value value = table.get(depth);
            
            if(value != null) {
               Type type = value.getType(scope);
            
               if(Function.class.isInstance(type)) {
                  return executeV(scope, Constraint.getInstance(type), type);
               }
               return Constraint.getNone(); // this is because we don't know that its not a function
            }
         }
//         Type t = null;
//         if(left != null) {
//            t =left.getType(scope);
//         } else {
//            t = scope.getType();
//         }
//         //String x = reference.getName(scope);
//
//         if(t != null) {
//            if(left == null){
//               left = Constraint.getInstance(t);
//            }
//         }
         if(left != null){
            Type t =left.getType(scope);
            if(t != null){
               return executeV(scope, left, t);
            }else {
              arguments.compile(scope); 
            }
            return Constraint.getNone();
         }
         return executeV(scope, null, null);         
      }
      
      private Constraint executeV(Scope scope, Constraint left, Type type) throws Exception {
         String name = reference.getName(scope); 
         Type[] array = arguments.compile(scope); 
         CallDispatcher handler = site.get(scope, left);
         Constraint result = handler.compile(scope, type, array);
         
         for(Evaluation evaluation : evaluations) {
            if(result == null) {
               throw new InternalStateException("Result of '" + name + "' null"); 
            }
            result = evaluation.compile(scope, result);
         }
         return result; 
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
         Object[] array = arguments.create(scope); 
         CallDispatcher handler = site.get(scope, left);
         Value value = handler.dispatch(scope, left, array);
         
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