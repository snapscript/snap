package org.snapscript.tree.resume;

import org.snapscript.core.resume.Promise;
import org.snapscript.core.result.Result;
import org.snapscript.core.resume.Resume;
import org.snapscript.core.scope.Scope;
import org.snapscript.tree.Suspend;

public class AwaitResume extends Suspend<Object, Resume> {

   private final Object state;
   private final Resume child;

   public AwaitResume(Resume child, Object state){
      this.child = child;
      this.state = state;
   }

   @Override
   public Result resume(Scope scope, Object value) throws Exception {
      if(state != null) {
         if(Promise.class.isInstance(state)) {
            Promise promise = (Promise)state;
            Object object = promise.get();

            return child.resume(scope, object);
         }
         return child.resume(scope, state);
      }
      return child.resume(scope, null);
   }

   @Override
   public Resume suspend(Result result, Resume resume, Resume value) throws Exception {
      return null;
   }
}
