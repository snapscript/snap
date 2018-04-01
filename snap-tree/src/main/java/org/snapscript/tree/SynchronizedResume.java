package org.snapscript.tree;

import org.snapscript.core.result.Result;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.yield.Resume;

public class SynchronizedResume extends Suspend<Object, Resume> {
   
   private final Resume parent;
   private final Resume child;
   
   public SynchronizedResume(Resume child, Resume parent){
      this.parent = parent;
      this.child = child;
   }
   
   @Override
   public Result resume(Scope scope, Object value) throws Exception {
      return parent.resume(scope, child);
   }

   @Override
   public Resume suspend(Result result, Resume resume, Resume value) throws Exception {
      return null;
   }
}