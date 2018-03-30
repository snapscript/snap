package org.snapscript.core;

import java.util.Set;

import org.snapscript.core.function.Function;

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
