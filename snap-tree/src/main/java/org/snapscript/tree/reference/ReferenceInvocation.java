package org.snapscript.tree.reference;

import static org.snapscript.core.error.Reason.ACCESS;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.function.bind.FunctionBinder;
import org.snapscript.core.function.bind.FunctionMatcher;
import org.snapscript.core.function.dispatch.FunctionDispatcher;
import org.snapscript.core.function.dispatch.FunctionDispatcher.Call;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.index.Index;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceEvaluation;
import org.snapscript.core.trace.TraceInterceptor;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;
import org.snapscript.tree.ArgumentList;
import org.snapscript.tree.ModifierAccessVerifier;
import org.snapscript.tree.NameReference;

public class ReferenceInvocation implements Compilation {

   private final Evaluation[] evaluations;
   private final NameReference reference;
   private final ArgumentList arguments;
   private final Evaluation function;
   
   public ReferenceInvocation(Evaluation function, ArgumentList arguments, Evaluation... evaluations) {
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
      Evaluation ev = new ReferenceProperty(function, evaluations).compile(module, path, line);
      return new CompileResult(matcher, arguments, evaluations, name, ev);     
   }
   
   private static class CompileResult extends Evaluation {
   
      private final ModifierAccessVerifier verifier;
      private final Evaluation[] evaluations; // func()[1][x]
      private final FunctionMatcher matcher;
      private final ArgumentList arguments;
      private final AtomicInteger offset;
      private final String name;
      private Evaluation ev;
      
      public CompileResult(FunctionMatcher matcher, ArgumentList arguments, Evaluation[] evaluations, String name, Evaluation ev) {
         this.verifier = new ModifierAccessVerifier();
         this.offset = new AtomicInteger();
         this.evaluations = evaluations;
         this.arguments = arguments;
         this.matcher = matcher;
         this.name = name;
         this.ev = ev;
         if(arguments.count() != 0)
            ignore.getAndIncrement();
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
         int count = arguments.count();
         if(count == 0) {
            try{
               ev.compile(scope, left);
            }catch(Exception e){
               ev=null;
            }
         }
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
      static AtomicInteger x = new AtomicInteger();
      static AtomicInteger fail = new AtomicInteger();
      static AtomicInteger ignore = new AtomicInteger();
      static AtomicInteger revert= new AtomicInteger();
      static AtomicInteger optimistic= new AtomicInteger();
//      static {
//         new Thread(new Runnable(){
//            public void run(){
//               while(true){
//                  try{
//                     Thread.sleep(1000);
//                     System.err.println("###############################################:  succes: "+x + " fail: "+fail + " ignore: "+ignore + " revert: "+revert + " optimistic: "+optimistic);
//                  } catch(Exception e){}
//               }
//            }
//         }).start();
//      }
      AtomicBoolean start = new AtomicBoolean();
      AtomicBoolean end = new AtomicBoolean();

      FunctionDispatcher dispatcher;
      Call call;
     // Type t;
      
      boolean revertToOldWay = false;
      
      @Override      
      public Value evaluate(Scope scope, Object left) throws Exception {

         Object[] array = arguments.create(scope);
//         if(scope != null){
//            return  matcher.match(scope, left).dispatch(scope, left, array).call(true, scope, left, array);
//         }
         if(call != null){
            try{
               //value =dispatcher.dispatch(scope, left, array).call(true, scope, left, array);
              // if(tt == t || tt.getType()!=null) {
                  return call.call(false, scope, left, array);
             //  }else {
             //     value = call.call(false, scope, left, array);
                  //value = dispatcher.dispatch(scope, left, array).call(true, scope, left, array);
             //  }
            }catch(Throwable e){
               e.printStackTrace();
               revertToOldWay=true;
               revert.getAndIncrement();
               optimistic.getAndDecrement();
               //e.printStackTrace();
               return matcher.match(scope, left).dispatch(scope, left, array).call(true, scope, left, array);
              //throw new RuntimeException(e);
            }
         }
         int count = arguments.count();
         Value value = null;
         
         if(dispatcher == null) {
            dispatcher = matcher.match(scope, left);
         }

            if(revertToOldWay) {
               value = matcher.match(scope, left).dispatch(scope, left, array).call(true, scope, left, array);
            } else {
               if(Module.class.isInstance(left)||Type.class.isInstance(left)||"start".equals(name)) {
                  value =dispatcher.dispatch(scope, left, array).call(true, scope, left, array);
               }else {
                  //Type tt = scope.getModule().getContext().getExtractor().getType(left);
                  if(call == null) {
                     call = dispatcher.dispatch(scope, left, array);
                 //    t = tt;
                     optimistic.getAndIncrement();
                  }
                  try{
                     //value =dispatcher.dispatch(scope, left, array).call(true, scope, left, array);
                    // if(tt == t || tt.getType()!=null) {
                        value = call.call(false, scope, left, array);
                   //  }else {
                   //     value = call.call(false, scope, left, array);
                        //value = dispatcher.dispatch(scope, left, array).call(true, scope, left, array);
                   //  }
                  }catch(Throwable e){
                     revertToOldWay=true;
                     revert.getAndIncrement();
                     optimistic.getAndDecrement();
                     //e.printStackTrace();
                     value = matcher.match(scope, left).dispatch(scope, left, array).call(true, scope, left, array);
                    //throw new RuntimeException(e);
                  }
               }
            } 

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