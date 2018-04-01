package org.snapscript.tree;

import org.snapscript.core.Execution;
import org.snapscript.core.result.Result;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.yield.Resume;
import org.snapscript.core.yield.Yield;

public abstract class SuspendStatement<T> extends Execution implements Resume<T, T> {

   protected Result suspend(Scope scope, Result result, Resume child, T value) throws Exception {
      Resume parent = suspend(result, child, value);
      Yield yield = result.getValue();
      Object object = yield.getValue();
      
      return Result.getYield(object, scope, parent);
   }
}
