package org.snapscript.core.yield;

import org.snapscript.core.Result;
import org.snapscript.core.Scope;

public class NoResume implements Resume {
   
   @Override
   public Result resume(Scope scope, Object value) throws Exception {
      return Result.getNormal();
   }

   @Override
   public Resume suspend(Result result, Resume resume, Object o) throws Exception {
      return null;
   }
}