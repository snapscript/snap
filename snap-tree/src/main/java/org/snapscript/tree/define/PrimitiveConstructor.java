package org.snapscript.tree.define;

import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.define.Initializer;

public class PrimitiveConstructor extends Initializer {

   @Override
   public Result execute(Scope scope, Type type) throws Exception {  
      Scope outer = scope.getOuter();
      return ResultType.getNormal(outer);
   }

}
