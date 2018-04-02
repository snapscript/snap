package org.snapscript.tree.compile;

import org.snapscript.core.function.Function;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.index.LocalScopeExtractor;
import org.snapscript.core.type.Type;

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
