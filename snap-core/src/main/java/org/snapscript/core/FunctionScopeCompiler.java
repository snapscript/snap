package org.snapscript.core;

import org.snapscript.core.function.Function;

public class FunctionScopeCompiler extends ScopeCompiler {
   
   private final LocalScopeExtractor extractor;
   
   public FunctionScopeCompiler() {
      this.extractor = new LocalScopeExtractor(false, true);          
   }

   public Scope compile(Scope local, Type type, Function function) throws Exception {    
      Scope scope = extractor.extract(local);
      Scope stack = scope.getStack();
      
      compileParameters(stack, function);

      return stack;
   }
}
