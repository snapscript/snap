package org.snapscript.core.function.dispatch;

import static org.snapscript.core.constraint.Constraint.NONE;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Connection;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;

public class EmptyDispatcher implements FunctionDispatcher {
   
   private final Connection connection;
   
   public EmptyDispatcher() {
      this.connection = new EmptyConnection();
   }

   @Override
   public Constraint compile(Scope scope, Constraint constraint, Type... arguments) throws Exception {
      return NONE;
   }

   @Override
   public Connection dispatch(Scope scope, Value value, Object... arguments) throws Exception {
      return connection;
   }
   
   private static class EmptyConnection implements Connection<Value> {

      public EmptyConnection() {
         super();
      }

      @Override
      public boolean accept(Scope scope, Object object, Object... arguments) throws Exception {
         return false;
      }
      
      @Override
      public Object invoke(Scope scope, Value value, Object... arguments) throws Exception {
         return null;
      }
   }
}      
