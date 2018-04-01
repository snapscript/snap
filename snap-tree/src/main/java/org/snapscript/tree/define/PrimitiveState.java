package org.snapscript.tree.define;

import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Allocation;
import org.snapscript.core.result.Result;

public class PrimitiveState extends Allocation {

   @Override
   public Result execute(Scope scope, Type type) throws Exception {  
      Scope outer = scope.getScope();
      return Result.getNormal(outer);
   }

}