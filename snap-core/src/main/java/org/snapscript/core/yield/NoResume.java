package org.snapscript.core.yield;

import static org.snapscript.core.result.Result.NORMAL;

import org.snapscript.core.result.Result;
import org.snapscript.core.scope.Scope;

public class NoResume implements Resume {
   
   @Override
   public Result resume(Scope scope, Object value) throws Exception {
      return NORMAL;
   }

   @Override
   public Resume suspend(Result result, Resume resume, Object o) throws Exception {
      return null;
   }
}