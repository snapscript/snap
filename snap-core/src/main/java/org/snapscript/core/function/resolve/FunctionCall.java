package org.snapscript.core.function.resolve;

import java.util.concurrent.Callable;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.index.FunctionPointer;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;

public class FunctionCall implements Callable<Value> {
   
   private final FunctionPointer pointer;
   private final Value value;
   private final Scope scope;
   private final Object[] list;
   
   public FunctionCall(FunctionPointer pointer, Scope scope, Value value, Object... list) {
      this.pointer = pointer;
      this.value = value;
      this.scope = scope;
      this.list = list;
   }

   public Constraint check(Constraint left) throws Exception {
      return pointer.getConstraint(scope, left);
   }
   
   @Override
   public Value call() throws Exception {
      Object source = value.getValue();
      Module module = scope.getModule();
      Invocation invocation = pointer.getInvocation();
      Object result = invocation.invoke(scope, source, list);
      
      return Value.getTransient(result, module);
   }
}