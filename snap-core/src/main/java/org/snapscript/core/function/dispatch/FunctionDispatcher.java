package org.snapscript.core.function.dispatch;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;
                   
public interface FunctionDispatcher<T> {
   Constraint compile(Scope scope, Constraint object, Type... arguments) throws Exception;
   Call<T> dispatch(Scope scope, T object, Object... arguments) throws Exception;
   public static class Call<T>{
      public Value call(boolean skip, Scope scope, T object, Object... arguments) throws Exception{
         return Value.NULL;
      }
   }
}