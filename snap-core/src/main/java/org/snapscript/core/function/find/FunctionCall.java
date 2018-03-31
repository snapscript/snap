package org.snapscript.core.function.find;

import java.util.concurrent.Callable;

import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;

public class FunctionCall implements Callable<Value> {
   
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
   
   public Constraint check() throws Exception {
      Function function = pointer.getFunction();      
      return function.getConstraint();
   }
   
   @Override
   public Value call() throws Exception {
      Invocation invocation = pointer.getInvocation();
      Object result = invocation.invoke(scope, source, list);
      
      return Value.getTransient(result);
   }
}