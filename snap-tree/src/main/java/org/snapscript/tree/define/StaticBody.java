package org.snapscript.tree.define;

import java.util.concurrent.atomic.AtomicBoolean;

import org.snapscript.core.Bug;
import org.snapscript.core.Execution;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;

public class StaticBody extends Statement {

   private final StaticExecution execution;
   private final TypeFactory factory;
   private final Type type;
   
   public StaticBody(TypeFactory factory, Type type) {
      this.execution = new StaticExecution(factory, type);
      this.factory = factory;
      this.type = type;
   }

   @Override
   public void define(Scope scope) throws Exception {
      //factory.define(scope, type);
   }

   @Override
   public Execution compile(Scope scope) throws Exception {
      //factory.compile(scope, type);
      return execution;
   }
   
   @Bug("make this a top level class call it StaticBody")
   public static class StaticExecution extends Execution {
      
      private final AtomicBoolean execute;
      private final TypeFactory factory;
      private final Result normal;
      private final Type type;
      
      public StaticExecution(TypeFactory factory, Type type) {
         this.execute = new AtomicBoolean(false);
         this.normal = Result.getNormal();
         this.factory = factory;
         this.type = type;
      }

      @Override
      public Result execute(Scope scope) throws Exception {
         if(execute.compareAndSet(false, true)) {
            Scope outer = type.getScope();
            factory.allocate(outer, type);
         }
         return normal;
      } 
   }
}