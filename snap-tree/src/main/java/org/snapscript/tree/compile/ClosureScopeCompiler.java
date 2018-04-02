package org.snapscript.tree.compile;

import org.snapscript.core.function.Function;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class ClosureScopeCompiler extends ScopeCompiler{

   public ClosureScopeCompiler() {
      super();
   }
   
   public Scope compile(Scope closure, Type type, Function function) throws Exception {
      Scope scope = closure.getStack();
      
      compileParameters(scope, function);
      compileProperties(scope, type);
      
      return scope;
   }
}
