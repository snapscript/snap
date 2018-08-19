package org.snapscript.tree.define;

import org.snapscript.core.function.Function;
import org.snapscript.core.function.FunctionBody;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeState;
import org.snapscript.tree.compile.TypeScopeCompiler;
import org.snapscript.tree.constraint.FunctionName;

public class FunctionBodyCompiler extends TypeState {

   private final TypeScopeCompiler compiler;
   private final FunctionBody body;
   
   public FunctionBodyCompiler(FunctionName identifier, FunctionBody body) {
      this.compiler = new TypeScopeCompiler(identifier);
      this.body = body;
   }
   
   @Override
   public void compile(Scope scope, Type type) throws Exception {
      Function function = body.create(scope);
      Scope outer = compiler.compile(scope, type, function);

      body.compile(outer);
   }
}
