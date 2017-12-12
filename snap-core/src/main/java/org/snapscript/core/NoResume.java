package org.snapscript.core;

public class NoResume implements Resume {
   
   @Override
   public Result resume(Scope scope, Object value) throws Exception {
      return Result.getNormal();
   }

   @Override
   public Resume create(Result result, Resume resume, Object o) throws Exception {
      return null;
   }
}