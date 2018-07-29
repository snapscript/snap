package org.snapscript.tree.function;

import static org.snapscript.core.constraint.Constraint.NONE;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.bind.FunctionBinder;
import org.snapscript.core.function.bind.FunctionMatcher;
import org.snapscript.core.function.dispatch.FunctionDispatcher;
import org.snapscript.core.link.ImplicitImportLoader;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.index.Index;
import org.snapscript.core.scope.index.LocalScopeFinder;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceEvaluation;
import org.snapscript.core.trace.TraceInterceptor;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;
import org.snapscript.tree.ArgumentList;
import org.snapscript.tree.NameReference;
import org.snapscript.tree.variable.Variable;

public class FunctionInvocation implements Compilation {

   private final Evaluation[] evaluations;
   private final NameReference reference;
   private final ArgumentList arguments;
   Evaluation function;
   public FunctionInvocation(Evaluation function, ArgumentList arguments, Evaluation... evaluations) {
      this.reference = new NameReference(function);
      this.evaluations = evaluations;
      this.arguments = arguments;
      this.function = function;
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
      Evaluation ev = new Variable(function).compile(module, path, line);
      return new CompileResult(matcher, arguments, evaluations, name, ev);     
   }
   
   private static class CompileResult extends Evaluation {   

      private final Evaluation[] evaluations; // func()[1][x]
      private final ImplicitImportLoader loader;
      private final LocalScopeFinder finder;
      private final FunctionMatcher matcher;
      private final ArgumentList arguments;
      private final AtomicInteger offset;    
      private final String name;
      private Evaluation ev;
      
      public CompileResult(FunctionMatcher matcher, ArgumentList arguments, Evaluation[] evaluations, String name, Evaluation ev) {
         this.loader = new ImplicitImportLoader();
         this.finder = new LocalScopeFinder();
         this.offset = new AtomicInteger(-1);
         this.evaluations = evaluations;
         this.arguments = arguments;
         this.matcher = matcher;
         this.name = name;
         this.ev = ev;
      }
      
      @Override
      public void define(Scope scope) throws Exception {
         int count = arguments.count();
         if(count == 0) {
            try{
               ev.define(scope);
            }catch(Exception e){
               e.printStackTrace();
               ev=null;
            }
         }
         Index index = scope.getIndex();
         int depth = index.get(name);

         if(depth == -1) {
            loader.loadImports(scope, name);
         } else {
            offset.set(depth);
         }
         arguments.define(scope);
         
         for(Evaluation evaluation : evaluations) {
            evaluation.define(scope);
         }
      }
      
      @Override
      public Constraint compile(Scope scope, Constraint left) throws Exception {
         int count = arguments.count();
         if(count == 0 && ev != null) {
            try{
               ev.compile(scope, left);
            }catch(Exception e){
               ev=null;
            }
         }
         int depth = offset.get();
         Value value = finder.findFunction(scope, name, depth);
 
         if(value != null) { 
            Type type = value.getType(scope);
            
            if(type == null) {
               arguments.compile(scope); 
               return NONE;
            }
            return compile(scope, name, value);            
         }
         return compile(scope, name);         
      }
      
      private Constraint compile(Scope scope, String name) throws Exception {
         Type[] array = arguments.compile(scope); 
         FunctionDispatcher dispatcher = matcher.match(scope);
         Constraint result = dispatcher.compile(scope, NONE, array);
         
         for(Evaluation evaluation : evaluations) {
            if(result == null) {
               throw new InternalStateException("Result of '" + name + "' is null"); 
            }
            result = evaluation.compile(scope, result);
         }
         return result; 
      }
      
      private Constraint compile(Scope scope, String name, Constraint local) throws Exception {
         Type[] array = arguments.compile(scope); 
         FunctionDispatcher dispatcher = matcher.match(scope);
         Constraint result = dispatcher.compile(scope, local, array);
         
         for(Evaluation evaluation : evaluations) {
            if(result == null) {
               throw new InternalStateException("Result of '" + name + "' is null"); 
            }
            result = evaluation.compile(scope, result);
         }
         return result; 
      }
      static AtomicInteger x = new AtomicInteger();
      static AtomicInteger fail = new AtomicInteger();
      static AtomicInteger ignore = new AtomicInteger();
//      static {
//         new Thread(new Runnable(){
//            public void run(){
//               while(true){
//                  try{
//                     Thread.sleep(1000);
//                     System.err.println("#################### VAR ###########################:  succes: "+x + " fail: "+fail + " ignore: "+ignore);
//                  } catch(Exception e){}
//               }
//            }
//         }).start();
//      }
      AtomicBoolean start = new AtomicBoolean();
      AtomicBoolean end = new AtomicBoolean();
      AtomicBoolean a= new AtomicBoolean();
      @Override
      public Value evaluate(Scope scope, Object left) throws Exception {
         int count = arguments.count();
         if(count == 0 && ev != null) {
            try{
               if(start.compareAndSet(false, true))
                  x.getAndIncrement();
               return ev.evaluate(scope, left);
            }catch(Exception e){
               if(end.compareAndSet(false,  true)){
                  x.getAndDecrement();
                  fail.getAndIncrement();
               }
               ev=null;
            }

         }
         int depth = offset.get();
         Value value = finder.findFunction(scope, name, depth);
            
         if(value != null) { 
            Object object = value.getValue();
            
            if(Function.class.isInstance(object)) {
               return evaluate(scope, name, value);
            }
         }
         return evaluate(scope, name);
      }
      
      private Value evaluate(Scope scope, String name) throws Exception {
         Object[] array = arguments.create(scope); 
         FunctionDispatcher dispatcher = matcher.match(scope);
         Value value = dispatcher.dispatch(scope, null, array).call(true, scope, null, array);
         
         for(Evaluation evaluation : evaluations) {
            Object result = value.getValue();
            
            if(result == null) {
               throw new InternalStateException("Result of '" + name + "' is null"); 
            }
            value = evaluation.evaluate(scope, result);
         }
         return value; 
      }
      
      private Value evaluate(Scope scope, String name, Object local) throws Exception {
         Object[] array = arguments.create(scope); 
         FunctionDispatcher dispatcher = matcher.match(scope, local);
         Value value = dispatcher.dispatch(scope, local, array).call(true, scope, local, array);
         
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