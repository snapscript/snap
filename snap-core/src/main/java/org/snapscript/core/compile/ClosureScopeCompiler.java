package org.snapscript.core.compile;

import java.util.Set;

import org.snapscript.core.Context;
import org.snapscript.core.function.Function;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeExtractor;

public class ClosureScopeCompiler extends ScopeCompiler{

   public ClosureScopeCompiler() {
      super();
   }
   
   public Scope compile(Scope closure, Type type, Function function) throws Exception {
      Scope scope = closure.getStack();
      
      compileParameters(scope, function);
      
      if(type != null){
         extractClosure(scope, type);
      }
      return scope;
   }
   
   protected void extractClosure(Scope scope, Type type) {
      Module module = scope.getModule();
      Context context = module.getContext();
      TypeExtractor extractor = context.getExtractor();      
      Set<Type> types = extractor.getTypes(type);      
      
      for(Type base : types){
         compileProperties(scope, base);
      }
   }
}
