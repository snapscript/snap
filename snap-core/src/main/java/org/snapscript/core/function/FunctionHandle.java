package org.snapscript.core.function;

import org.snapscript.core.scope.Scope;

public class FunctionHandle {
   
   protected final InvocationBuilder builder;
   protected final InvocationBuilder other;
   protected final Function function;
   
   public FunctionHandle(InvocationBuilder builder, InvocationBuilder other, Function function) {
      this.function = function;
      this.builder = builder;
      this.other = other;
   }
   
   public void define(Scope scope) throws Exception {
      builder.define(scope);
      
      if(other != null) {
         other.define(scope);
      }
   }   
   
   public void compile(Scope scope) throws Exception {
      builder.compile(scope);
      
      if(other != null) {
         other.compile(scope);
      }
   }
   
   public Function create(Scope scope) throws Exception {
      return function;
   }
}