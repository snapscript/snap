package org.snapscript.core.function.resolve;

import static org.snapscript.core.constraint.Constraint.NONE;

import java.util.List;
import java.util.concurrent.Callable;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.index.FunctionPointer;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;

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

   public Constraint check(Constraint left) throws Exception {
      Function function = pointer.getFunction();      
      Constraint returns = function.getConstraint();
      String name = returns.getName(scope);
      
      if(name != null) {
         List<Constraint> constraints = left.getGenerics(scope);
         
         if(!constraints.isEmpty()) {
            return NONE; // should really resolve type
         }
      }
      return returns;
   }
   
   @Override
   public Value call() throws Exception {
      Invocation invocation = pointer.getInvocation();
      Object result = invocation.invoke(scope, source, list);
      
      return Value.getTransient(result);
   }
}