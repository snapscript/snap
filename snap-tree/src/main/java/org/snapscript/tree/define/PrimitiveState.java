package org.snapscript.tree.define;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.TypeState;
import org.snapscript.core.type.Type;
import org.snapscript.core.result.Result;

public class PrimitiveState extends TypeState {

   @Override
   public Result execute(Scope scope, Type type) throws Exception {  
      Scope outer = scope.getScope();
      return Result.getNormal(outer);
   }

}