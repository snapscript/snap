package org.snapscript.tree.define;

import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;

public class PrimitiveConstructor extends TypeFactory {

   @Override
   public Result execute(Scope scope, Type type) throws Exception {  
      Scope outer = scope.getScope();
      return Result.getNormal(outer);
   }

}