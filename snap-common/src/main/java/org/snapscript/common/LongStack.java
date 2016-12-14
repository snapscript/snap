package org.snapscript.common;

public class LongStack {

   private long[] stack;
   private int count;

   public LongStack() {
      this(1024);
   }
   
   public LongStack(int capacity) {
      this.stack = new long[capacity];
   }      

   public boolean isEmpty() {
      return count == 0;
   }   

   public long get(int index) {
      if(index < count) {
         return stack[index];
      }
      return -1;
   }

   public boolean contains(long value) {
      for(int i = 0; i < count; i++) {
         long next = stack[i];
         
         if(next == value) {
            return true;
         }
      }
      return false; 
   }   

   public void push(long value) {
      int capacity = stack.length;
      
      if(count >= capacity) {
         long[] copy = new long[capacity * 2];
         
         if(count > 0) {
            System.arraycopy(stack, 0, copy, 0, stack.length);
         }
         stack = copy;
      }
      stack[count++] = value;
   }

   public long pop() {
      if(count > 0) {
         return stack[count-- -1];
      }
      return -1;
   }

   public long peek() {
      if(count > 0) {
         return stack[count -1];
      }
      return -1;
   }
   
   public int size() {
      return count;
   }

   public void clear() {
      count = 0;
   }
}
