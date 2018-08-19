package org.snapscript.core.scope.index;

import java.util.Arrays;
import java.util.Iterator;

import org.snapscript.common.EmptyIterator;
import org.snapscript.core.constraint.Constraint;

public class ArrayTable implements Table {

   private Constraint[] constraints;
   private Local[] locals;

   public ArrayTable() {
      this(0);
   }
   
   public ArrayTable(int count) {
      this.constraints = new Constraint[count];
      this.locals = new Local[count];
   }

   @Override
   public Iterator<Local> iterator() {
      if(locals.length > 0) {
         return new LocalIterator(locals);
      }
      return new EmptyIterator<Local>();
   }

   @Override
   public Local getLocal(int index) {
      if(index < locals.length && index >= 0) {
         return locals[index];
      }
      return null;
   }
   
   @Override
   public void addLocal(int index, Local local) {
      if(local == null) {
         throw new IllegalStateException("Local at index " + index + " is null");
      }
      if(index >= locals.length) {
         Local[] copy = new Local[index == 0 ? 2 : index * 2];
         
         for(int i = 0; i < locals.length; i++) {
            copy[i] = locals[i];
         }
         locals = copy;
      }
      locals[index] = local;
   }

   @Override
   public Constraint getConstraint(int index) {
      if(index < constraints.length && index >= 0) {
         return constraints[index];
      }
      return null;
   }
   
   @Override
   public void addConstraint(int index, Constraint constraint) {
      if(constraint == null) {
         throw new IllegalStateException("Constraint at index " + index + " is null");
      }
      if(index >= constraints.length) {
         Constraint[] copy = new Constraint[index == 0 ? 2 : index * 2];
         
         for(int i = 0; i < constraints.length; i++) {
            copy[i] = constraints[i];
         }
         constraints = copy;
      }
      constraints[index] = constraint;
   }
   
   @Override
   public String toString() {
      return Arrays.toString(locals);
   }
   
   private static class LocalIterator implements Iterator<Local> {
      
      private Local[] table;
      private Local local;
      private int index;

      public LocalIterator(Local[] table) {
         this.table = table;
      }
      
      @Override
      public boolean hasNext() {
         while(local == null) {
            if(index >= table.length) {
               break;
            }
            local = table[index++];
         }
         return local != null;
      }

      @Override
      public Local next() {
         Local next = null;
         
         if(hasNext()) {
            next = local;
            local = null;
         }
         return next;
      }
   }
}
