package org.snapscript.tree.compile;

import org.snapscript.core.function.Function;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.tree.constraint.GenericList;

public class ClosureScopeCompiler extends FunctionScopeCompiler{
   
   public ClosureScopeCompiler(GenericList generics) {
      super(generics);
   }
   
   @Override
   public Scope compile(Scope closure, Type type, Function function) throws Exception {
      Scope scope = super.compile(closure, type, function);      
      compileProperties(scope, type);      
      return scope;
   }
   
   public Scope extract(Scope closure, Type type) throws Exception {
      return extractor.extract(closure);
   }
}
