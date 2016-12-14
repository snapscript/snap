package org.snapscript.common;

public class IntegerStack {

   private int[] stack;
   private int count;

   public IntegerStack() {
      this(1024);
   }
   
   public IntegerStack(int capacity) {
      this.stack = new int[capacity];
   }      

   public boolean isEmpty() {
      return count == 0;
   }   

   public int get(int index) {
      if(index < count) {
         return stack[index];
      }
      return -1;
   }

   public boolean contains(int value) {
      for(int i = 0; i < count; i++) {
         int next = stack[i];
         
         if(next == value) {
            return true;
         }
      }
      return false; 
   }   

   public void push(int value) {
      int capacity = stack.length;
      
      if(count >= capacity) {
         int[] copy = new int[capacity * 2];
         
         if(count > 0) {
            System.arraycopy(stack, 0, copy, 0, stack.length);
         }
         stack = copy;
      }
      stack[count++] = value;
   }

   public int pop() {
      if(count > 0) {
         return stack[count-- -1];
      }
      return -1;
   }

   public int peek() {
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
