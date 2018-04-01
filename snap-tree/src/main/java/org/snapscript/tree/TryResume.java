package org.snapscript.tree;

import org.snapscript.core.result.Result;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.yield.Resume;

public class TryResume extends Suspend<Object, Object> {
   
   private final Resume parent;
   private final Resume child;
   
   public TryResume(Resume child, Resume parent){
      this.parent = parent;
      this.child = child;
   }
   
   @Override
   public Result resume(Scope scope, Object value) throws Exception {
      return parent.resume(scope, child);
   }

   @Override
   public Resume suspend(Result result, Resume resume, Object value) throws Exception {
      return null;
   }
}