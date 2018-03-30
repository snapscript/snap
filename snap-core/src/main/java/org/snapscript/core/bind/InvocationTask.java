package org.snapscript.core.bind;

import java.util.concurrent.Callable;

import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Invocation;

public class InvocationTask implements Callable<Value> {
   
   private final FunctionCall pointer;
   private final Object source;
   private final Scope scope;
   private final Object[] list;
   
   public InvocationTask(FunctionCall pointer, Scope scope, Object source, Object... list) {
      this.pointer = pointer;
      this.source = source;
      this.scope = scope;
      this.list = list;
   }
   
   public Constraint getReturn(){
      return pointer.getFunction().getConstraint();
   }
   
   @Override
   public Value call() throws Exception {
      Invocation invocation = pointer.getInvocation();
      Object result = invocation.invoke(scope, source, list);
      
      return Value.getTransient(result);
   }
}