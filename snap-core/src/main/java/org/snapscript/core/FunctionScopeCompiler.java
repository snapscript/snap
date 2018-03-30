package org.snapscript.core;

import org.snapscript.core.function.Function;

public class FunctionScopeCompiler extends ScopeCompiler {
   
   public FunctionScopeCompiler() {
      super();
   }

   public Scope compile(Scope local, Type type, Function function) throws Exception {    
      Scope scope = local.getScope();
      Scope outer = scope.getStack();

      compileParameters(outer, function);

      return outer;
   }
}
