package org.snapscript.tree.condition;

import static org.snapscript.core.result.Result.NORMAL;

import org.snapscript.core.result.Result;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.yield.Resume;
import org.snapscript.core.yield.Yield;
import org.snapscript.tree.Suspend;

public class LoopResume extends Suspend<Object, Object> {
   
   private final Resume parent;
   private final Resume child;
   
   public LoopResume(Resume child, Resume parent){
      this.parent = parent;
      this.child = child;
   }
   
   @Override
   public Result resume(Scope scope, Object value) throws Exception {
      Result result = child.resume(scope, value);
      
      if(result.isYield()) {
         return suspend(scope, result, parent, null);
      }
      if(result.isReturn()) {
         return result;
      }
      if(result.isBreak()) {
         return NORMAL;
      }
      return parent.resume(scope, null);
   }

   @Override
   public Resume suspend(Result result, Resume resume, Object value) throws Exception {
      Yield yield = result.getValue();
      Resume child = yield.getResume();
      
      return new LoopResume(child, resume);
   }
}