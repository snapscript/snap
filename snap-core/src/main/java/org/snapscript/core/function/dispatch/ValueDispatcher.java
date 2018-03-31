package org.snapscript.core.function.dispatch;

import static org.snapscript.core.constraint.Constraint.NONE;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.find.FunctionCall;
import org.snapscript.core.function.find.FunctionFinder;

public class ValueDispatcher implements FunctionDispatcher<Value> {
   
   private final FunctionFinder binder;
   private final String name;
   
   public ValueDispatcher(FunctionFinder binder, String name) {
      this.binder = binder;
      this.name = name;
   }
   
   @Override
   public Constraint compile(Scope scope, Type value, Type... arguments) throws Exception {
      return NONE;
   }

   @Override
   public Value dispatch(Scope scope, Value value, Object... arguments) throws Exception {
      FunctionCall closure = binder.bindValue(value, arguments); // function variable
      
      if(closure == null) {
         throw new InternalStateException("Method '" + name + "' not found in scope");
      }
      return closure.call();   
   }
}