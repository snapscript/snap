package org.snapscript.tree.compile;

import org.snapscript.core.function.Function;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.tree.constraint.GenericList;
import org.snapscript.tree.constraint.GenericParameterExtractor;

public class ClosureScopeCompiler extends ScopeCompiler{
   
   private final GenericParameterExtractor extractor;

   public ClosureScopeCompiler(GenericList generics) {
      this.extractor = new GenericParameterExtractor(generics);
   }
   
   @Override
   public Scope define(Scope closure, Type type) throws Exception {
      return extractor.extract(closure);
   }
   
   @Override
   public Scope compile(Scope closure, Type type, Function function) throws Exception {
      Scope scope = extractor.extract(closure);
      
      compileParameters(scope, function);
      compileProperties(scope, type);
      
      return scope;
   }
   
   public Scope extract(Scope closure, Type type) throws Exception {
      return extractor.extract(closure);
   }
}
