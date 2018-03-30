package org.snapscript.core.dispatch;

import static org.snapscript.core.constraint.Constraint.NONE;

import java.util.concurrent.Callable;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.core.constraint.Constraint;

public class ValueDispatcher implements CallDispatcher<Value> {
   
   private final FunctionBinder binder;
   private final String name;
   
   public ValueDispatcher(FunctionBinder binder, String name) {
      this.binder = binder;
      this.name = name;
   }
   
   @Override
   public Constraint compile(Scope scope, Type value, Type... arguments) throws Exception {
      return NONE;
   }

   @Override
   public Value dispatch(Scope scope, Value value, Object... arguments) throws Exception {
      Callable<Value> closure = binder.bindValue(value, arguments); // function variable
      
      if(closure == null) {
         throw new InternalStateException("Method '" + name + "' not found in scope");
      }
      return closure.call();   
   }
}