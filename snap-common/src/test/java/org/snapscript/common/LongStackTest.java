package org.snapscript.common;

import java.security.SecureRandom;
import java.util.Random;

import junit.framework.TestCase;

public class LongStackTest extends TestCase {

   private static final int COUNT = 1000;
   
   public void testStackByComparison() {
      LongStack stack = new LongStack();
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
