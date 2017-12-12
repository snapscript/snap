package org.snapscript.tree.condition;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Result;
import org.snapscript.core.Resume;
import org.snapscript.core.Scope;
import org.snapscript.core.Yield;
import org.snapscript.tree.Suspend;

public class ForResume extends Suspend<Object, Object> {
   
   private final Evaluation assignment;
   private final Resume parent;
   private final Resume child;
   private final Result normal;
   
   public ForResume(Resume child, Resume parent, Evaluation assignment){
      this.normal = Result.getNormal();
      this.assignment = assignment;
      this.parent = parent;
      this.child = child;
   }
   
   @Override
   public Result resume(Scope scope, Object value) throws Exception {
      Result result = child.resume(scope, value);
      
      if(result.isYield()) {
         return suspend(scope, result, parent, assignment);
      }
      if(result.isReturn()) {
         return result;
      }
      if(result.isBreak()) {
         return normal;
      }
      if(assignment != null) {
         assignment.evaluate(scope, null);
      }
      return parent.resume(scope, null);
   }

   @Override
   public Resume create(Result result, Resume resume, Object value) throws Exception {
      Yield yield = result.getValue();
      Resume child = yield.getResume();
      
      return new ForResume(child, resume, assignment);
   }
}