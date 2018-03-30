package org.snapscript.core;

import static org.snapscript.core.Reserved.TYPE_THIS;

import java.util.Set;

import org.snapscript.core.function.Function;

public class TypeScopeCompiler extends ScopeCompiler{
   
   public TypeScopeCompiler() {
      super();
   }

   public Scope compile(Scope x, Type type, Function function) throws Exception {          
      Scope scope = type.getScope();
      Scope outer = scope.getStack();

      compileParameters(outer, function);
      extractThis(outer, type);

      return outer;
   }
   
   protected void extractThis(Scope scope, Type type) {
      Value value = Value.getTransient(scope, type);
      Module module = scope.getModule();
      Context context = module.getContext();
      TypeExtractor extractor = context.getExtractor();      
      Set<Type> types = extractor.getTypes(type);      
      State state = scope.getState();
      
      for(Type base : types){
         compileProperties(scope, base);
      }
      state.add(TYPE_THIS, value);
   }
}
