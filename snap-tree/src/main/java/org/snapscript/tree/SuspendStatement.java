package org.snapscript.tree;

import org.snapscript.core.Result;
import org.snapscript.core.Resume;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Yield;

public abstract class SuspendStatement<T> extends Statement implements Resume<T, T> {

   protected Result suspend(Scope scope, Result result, Resume child, T value) throws Exception {
      Resume parent = suspend(result, child, value);
      Yield yield = result.getValue();
      Object object = yield.getValue();
      
      return Result.getYield(object, scope, parent);
   }
}
