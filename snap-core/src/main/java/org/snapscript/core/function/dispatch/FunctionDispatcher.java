package org.snapscript.core.function.dispatch;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.resolve.FunctionCall;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;
                   
public interface FunctionDispatcher {
   Constraint compile(Scope scope, Constraint constraint, Type... arguments) throws Exception;
   Call2 dispatch(Scope scope, Value value, Object... arguments) throws Exception;
   
   
   public static class Call{
      public Value call(boolean skip, Scope scope, Object object, Object... arguments) throws Exception{
         return Value.NULL;
      }
   }
   
   public static class Call2 implements Invocation {
    
      protected final FunctionCall call;
      
      public Call2(FunctionCall call) {
         this.call = call;
      }
      
      public Object invoke(Scope scope, Object object, Object... arguments) throws Exception{
         return null;
      }
   }
}