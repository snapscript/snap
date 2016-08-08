package org.snapscript.core.define;

import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;

public abstract class Initializer {

   public Result compile(Scope scope, Type type) throws Exception { // static stuff
      return ResultType.getNormal();
   }
   
   public Result execute(Scope scope, Type type) throws Exception { // instance stuff
      return ResultType.getNormal();
   }
}