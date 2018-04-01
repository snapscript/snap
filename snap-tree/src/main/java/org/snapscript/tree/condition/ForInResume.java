package org.snapscript.tree.condition;

import static org.snapscript.core.result.Result.NORMAL;

import java.util.Iterator;

import org.snapscript.core.result.Result;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.yield.Resume;
import org.snapscript.core.yield.Yield;
import org.snapscript.tree.Suspend;

public class ForInResume extends Suspend<Object, Iterator> {
   
   private final Iterator iterator;
   private final Resume parent;
   private final Resume child;
   
   public ForInResume(Resume child, Resume parent, Iterator iterator){
      this.iterator = iterator;
      this.parent = parent;
      this.child = child;
   }
   
   @Override
   public Result resume(Scope scope, Object ignore) throws Exception {
      Result result = child.resume(scope, null);
      
      if(result.isYield()) {
         return suspend(scope, result, parent, iterator);
      }
      if(result.isReturn()) {
         return result;
      }
      if(result.isBreak()) {
         return NORMAL;
      }
      return parent.resume(scope, iterator);
   }

   @Override
   public Resume suspend(Result result, Resume resume, Iterator value) throws Exception {
      Yield yield = result.getValue();
      Resume child = yield.getResume();
      
      return new ForInResume(child, resume, value);
   }
}
