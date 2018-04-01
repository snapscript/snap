package org.snapscript.tree.condition;

import static org.snapscript.core.result.Result.NORMAL;

import org.snapscript.core.result.Result;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.yield.Resume;
import org.snapscript.core.yield.Yield;
import org.snapscript.tree.Suspend;

public class SwitchResume extends Suspend<Object, Integer> {
   
   private final Resume parent;
   private final Resume child;
   private final int index;
   
   public SwitchResume(Resume child, Resume parent, int index){
      this.parent = parent;
      this.child = child;
      this.index = index;
   }
   
   @Override
   public Result resume(Scope scope, Object value) throws Exception {
      Result result = child.resume(scope, value);
      
      if(result.isYield()) {
         return suspend(scope, result, parent, index);
      }
      if(result.isBreak()) {
         return NORMAL;
      }
      if(!result.isNormal()) {
         return result;      
      }
      return parent.resume(scope, index + 1);
   }

   @Override
   public Resume suspend(Result result, Resume resume, Integer value) throws Exception {
      Yield yield = result.getValue();
      Resume child = yield.getResume();
      
      return new SwitchResume(child, resume, value);
   }
}