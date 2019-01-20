package org.snapscript.core.resume;

import java.util.Iterator;

import org.snapscript.core.Context;
import org.snapscript.core.error.ErrorCauseExtractor;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.module.Module;
import org.snapscript.core.result.Result;
import org.snapscript.core.scope.Scope;

public class Yield<T> implements Iterable<T> {

   private final Resume next;
   private final Object result;
   private final Scope scope;
   
   public Yield(Object result) {
      this(result, null, null);
   }
   
   public Yield(Object result, Scope scope, Resume next) {
      this.result = result;
      this.scope = scope;
      this.next = next;
   }
   
   @Override
   public Iterator<T> iterator() {
      return new ResumeIterator<T>(result, scope, next);
   }
   
   public Resume getResume() { // resume statement
      if(next == null) {
         return new NoResume();
      }
      return next;
   }
   
   public <T> T getValue() {
      return (T)result;
   }
   
   private static class ResumeIterator<T> implements Iterator<T> {

      private ErrorCauseExtractor extractor;
      private Resume resume;
      private Object value;
      private Scope scope;
      
      public ResumeIterator(Object value, Scope scope, Resume resume) {
         this.extractor = new ErrorCauseExtractor();
         this.resume = resume;
         this.value = value;
         this.scope = scope;
      }

      @Override
      public boolean hasNext() {
         if(value == null && resume != null) {
            return resume();
         }
         return true;
      }

      @Override
      public T next() {
         if(hasNext()) {
            Object result = value;
            value = null;
            return (T)result;
         }
         return null;
      }

      @Override
      public void remove() {
         value = null;
      }

      private boolean resume() {
         try{
            Result result = resume.resume(scope, null);

            if(result.isYield()) {
               Yield yield = result.getValue();

               resume = yield.getResume();
               value = yield.getValue();
               return true;
            }
            if(result.isReturn()) {
               value = result.getValue();
               resume = null;
               return true;
            }
         }catch(Throwable e){
            throw new ResumeException("Could not resume after yield", e);
         }
         return false;
      }

   }
}
