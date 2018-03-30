package org.snapscript.core.trace;

import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.result.Result;

public class TraceTypeFactory extends TypeFactory {

   private final ErrorHandler handler;
   private final TypeFactory factory;
   private final Trace trace;
   
   public TraceTypeFactory(ErrorHandler handler, TypeFactory factory, Trace trace) {
      this.handler = handler;
      this.factory = factory;
      this.trace = trace;
   }
   
   @Override
   public void define(Scope scope, Type type) throws Exception {
      try {
         factory.define(scope, type);
      }catch(Exception cause) {
         handler.throwInternalError(scope, cause, trace);
      }
   }
   
   @Override
   public void compile(Scope scope, Type type) throws Exception {
      try {
         factory.compile(scope, type);
      }catch(Exception cause) {
         handler.throwInternalError(scope, cause, trace);
      }
   }
   
   @Override
   public void allocate(Scope scope, Type type) throws Exception {
      try {
         factory.allocate(scope, type);
      }catch(Exception cause) {
         handler.throwInternalError(scope, cause, trace);
      }
   }
   
   @Override
   public Result execute(Scope scope, Type type) throws Exception { 
      try {
         return factory.execute(scope, type);
      }catch(Exception cause) {
         handler.throwInternalError(scope, cause, trace);
      }
      return null;
   }

}
