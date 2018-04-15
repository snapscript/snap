package org.snapscript.tree.define;

import org.snapscript.core.function.Function;
import org.snapscript.core.function.FunctionBody;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.TypeState;
import org.snapscript.core.type.Type;
import org.snapscript.tree.compile.TypeScopeCompiler;

public class FunctionBodyCompiler extends TypeState {

   private final TypeScopeCompiler compiler;
   private final FunctionBody body;
   
   public FunctionBodyCompiler(FunctionBody body) {
      this.compiler = new TypeScopeCompiler();
      this.body = body;
   }
   
   @Override
   public void compile(Scope scope, Type type) throws Exception {
      Function function = body.create(scope);
      Scope outer = compiler.compile(scope, type, function);

      body.compile(outer);
   }
}
