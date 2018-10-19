package org.snapscript.core.function.resolve;

import static org.snapscript.core.constraint.Constraint.NONE;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.dispatch.FunctionDispatcher.Call2;
import org.snapscript.core.function.index.FunctionPointer;
import org.snapscript.core.function.index.ReturnType;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;

public class FunctionCall implements Invocation {
   
   private final FunctionPointer pointer;
   private final Object source;
   private final Scope scope;
   private final Object[] list;
   
   public FunctionCall(FunctionPointer pointer, Scope scope, Object source, Object... list) {
      this.pointer = pointer;
      this.source = source;
      this.scope = scope;
      this.list = list;
   }

   public Constraint check(Constraint left, Type... types) throws Exception {
      ReturnType type = pointer.getType(scope);

      if(type != null) {
         return type.check(left, types);
      }
      return NONE;
   }
   

   @Override
   public Object invoke(Scope scope, Object object, Object... list) throws Exception {
      Invocation invocation = pointer.getInvocation();
      
      if(invocation != null) {
         try {
            return invocation.invoke(scope, object, list);
         }catch(Exception e){
            e.printStackTrace();
            throw e;
         }
      }
      return null;
   }
//   
//   public Call2 create(FuncArgConverter converter) {
//      return new CallAdapter(pointer, converter);
//   }
//   
//   
////   @Override
////   public Value call() throws Exception {
////      Invocation invocation = pointer.getInvocation();
////      Object result = invocation.invoke(scope, source, list);
////
////      if(result != null) {
////         return Value.getTransient(result);
////      }
////      return NULL;
////   }
////   
//   
//   public static class CallAdapter extends Call2 {
//      
//      private final FuncArgConverter converter;
//      private final FunctionPointer pointer;
//      
//      public CallAdapter(FunctionPointer pointer, FuncArgConverter converter) {
//         this.converter = converter;
//         this.pointer = pointer;
//      }
//      
//      public Value call(Scope scope, Object source, Object... arguments) throws Exception{
//         try {
//            Object left = converter.convert(scope, source);
//            Invocation invocation = pointer.getInvocation();
//            Object result = invocation.invoke(scope, left, arguments);
//      
//            return Value.getTransient(result);
//         }catch(Exception e) {
//            throw new RuntimeException(e);
//         }
//      }
//   }
//   
//   
////   @Override
////   public Value call(boolean skip, Scope scope, Object source, Object... arguments) throws Exception {
//////      if(skip){
//////         return call();
//////      }
////
//////         if(scope != this.scope) {
//////            throw new RuntimeException("scope != this.scope");
//////         }
//////         if(source != this.source) {
//////            throw new RuntimeException("source != this.source");
//////         }
//////         if(arguments != this.list) {
//////            throw new RuntimeException("arguments != this.arguments");
//////         }
////   
////      if(this.scope == this.source) {
////         source = scope;
////      }
////      if(source instanceof Value) {
////         source = ((Value)source).getValue();
////      }
////      if(source instanceof Function) {
////         source = new FunctionAdapter((Function)source);
////      }
////      Invocation invocation = pointer.getInvocation();
////      Object result = invocation.invoke(scope, source, arguments);
////   
////      return Value.getTransient(result);
////
////      
////   }
//   
//   public static class FuncArgConverter {
//      
//      public Object convert(Scope scope, Object source) throws Exception {
//         return source;
//      }
//   }
//
//   
//   
}