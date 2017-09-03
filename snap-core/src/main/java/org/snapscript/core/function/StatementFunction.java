package org.snapscript.core.function;

import org.snapscript.core.Bug;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;

public class StatementFunction {
   
   private final InvocationBuilder builder;
   private final Statement statement;
   private final Function function;
   
   public StatementFunction(InvocationBuilder builder, Statement statement, Function function) {
      this.statement = statement;
      this.function = function;
      this.builder = builder;
   }
   
   @Bug("fix this")
   public Function getFunction(Scope scope) {
      return function;
   }
   
   public void compile(Scope scope) {
      try {
         builder.create(scope);
         
//         if(statement != null) {
//            statement.compile(scope);
//         }
      }catch(Exception e){
         e.printStackTrace();
      }
   }
}
