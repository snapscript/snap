package org.snapscript.core.yield;

import java.util.Iterator;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.result.Result;
import org.snapscript.core.scope.Scope;

public class Yield implements Iterable<Object> {

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
   public Iterator<Object> iterator() {
      return new ResumeIterator(result, scope, next);
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
   
   private static class ResumeIterator implements Iterator {

      private Resume resume;
      private Object value;
      private Scope scope;
      
      public ResumeIterator(Object value, Scope scope, Resume resume) {
         this.resume = resume;
         this.value = value;
         this.scope = scope;
      }
      
      @Override
      public boolean hasNext() {
         if(value == null && resume != null) {
            try{
               Result result = resume.resume(scope, null);
               
               if(result.isYield()) {
                  Yield yield = result.getValue();
                  
                  resume = yield.getResume();
                  value = yield.getValue();
                  return true;
               }
            }catch(Throwable e){
               throw new InternalStateException("Could not resume after yield", e);
            }
            return false;
         }
         return true;
      }

      @Override
      public Object next() {
         if(hasNext()) {
            Object result = value;
            value = null;
            return result;
         }
         return null;
      }
      
   }
}
