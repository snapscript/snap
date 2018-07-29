package org.snapscript.core.function.resolve;

import java.util.concurrent.Callable;

import org.snapscript.core.ModifierType;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.dispatch.FunctionDispatcher.Call;
import org.snapscript.core.function.index.FunctionPointer;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;

public class FunctionCall extends Call implements Callable<Value> {
   
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

   public Constraint check(Constraint left) throws Exception {
      return pointer.getConstraint(scope, left);
   }
   
   @Override
   public Value call() throws Exception {
      Invocation invocation = pointer.getInvocation();
      Object result = invocation.invoke(scope, source, list);
      
      return Value.getTransient(result);
   }

   @Override
   public Value call(boolean skip, Scope scope, Object source, Object... arguments) throws Exception {
      if(skip){
         return call();
      }

//         if(scope != this.scope) {
//            throw new RuntimeException("scope != this.scope");
//         }
//         if(source != this.source) {
//            throw new RuntimeException("source != this.source");
//         }
//         if(arguments != this.list) {
//            throw new RuntimeException("arguments != this.arguments");
//         }
   
         Invocation invocation = pointer.getInvocation();
         Object result = invocation.invoke(scope, source, arguments);
      
        return Value.getTransient(result);

      
   }
}