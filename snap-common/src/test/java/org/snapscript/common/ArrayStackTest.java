package org.snapscript.common;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import junit.framework.TestCase;

public class ArrayStackTest extends TestCase {
   
   private static final int COUNT = 1000;

   public void testStack() throws Exception {
      Stack<String> stack = new ArrayStack<String>();
      
      stack.push("a");
      stack.push("b");
      stack.push("c");
      
      assertEquals(stack.peek(), "c");
      assertEquals(stack.size(), 3);
      assertFalse(stack.isEmpty());
      
      List<String> list = new ArrayList<String>();
      
      for(String entry : stack){
         System.err.println(entry);
         list.add(entry);
      }
      assertEquals(list.get(0), "c");
      assertEquals(list.get(1), "b");
      assertEquals(list.get(2), "a");
   }
   
   public void testStackByComparison() {
      ArrayStack stack = new ArrayStack();
      java.util.Stack other = new java.util.Stack();
      Random random = new SecureRandom();
      
      for(int i = 0; i < COUNT; i++) {
         long value = random.nextInt(COUNT);
         stack.push(value);
         other.push(value);
         
         assertEquals(stack.peek(), other.peek());
         assertEquals(stack.size(), other.size());
      }
      
      for(int i = 0; i < COUNT; i++) {
         assertEquals(stack.pop(), other.pop());
         assertEquals(stack.size(), other.size());
      }
      assertEquals(stack.isEmpty(), other.isEmpty());
   }
}
