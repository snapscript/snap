package org.snapscript.tree;

import org.snapscript.core.result.Result;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.resume.Resume;

public class AwaitResume extends Suspend<Object, Resume> {

   private final Resume child;

   public AwaitResume(Resume child){
      this.child = child;
   }

   @Override
   public Result resume(Scope scope, Object value) throws Exception {
      return child.resume(scope, value);
   }

   @Override
   public Resume suspend(Result result, Resume resume, Resume value) throws Exception {
      return null;
   }
}
