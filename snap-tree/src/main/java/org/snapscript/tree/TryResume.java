package org.snapscript.tree;

import org.snapscript.core.Result;
import org.snapscript.core.Resume;
import org.snapscript.core.Scope;

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
   public Resume create(Result result, Resume resume, Object value) throws Exception {
      return null;
   }
}