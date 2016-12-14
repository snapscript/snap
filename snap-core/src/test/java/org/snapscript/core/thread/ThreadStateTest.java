package org.snapscript.core.thread;

import junit.framework.TestCase;

import org.snapscript.core.ValueType;
import org.snapscript.core.address.Address;
import org.snapscript.core.address.State2;

public class ThreadStateTest extends TestCase {
   
   private static final String[] DISPLAY = {
      "|",
      "|[]",
      "|[][]",
      "|[][][]",
      "|[][][][]"
   };

   public void testThreadState() throws Exception {
      ThreadState state = new ThreadState();
      StateTable table = new StateTable(state);
      int count = 0;
      
      System.err.println(DISPLAY[count]);
      table.add("a", "test-value-1");
      table.add("b", "test-value-2");
      
      assertEquals(table.get("a"), "test-value-1");
      assertEquals(table.get("b"), "test-value-2");
      
      state.mark(false);
      {
         System.err.println(DISPLAY[++count]);
         assertNull(table.get("a"));
         assertNull(table.get("b"));
      }
      state.reset();
      
      System.err.println(DISPLAY[--count]);
      assertEquals(table.get("a"), "test-value-1");
      assertEquals(table.get("b"), "test-value-2");
      
      state.mark(true);
      {
         System.err.println(DISPLAY[++count]);
         assertEquals(table.get("a"), "test-value-1");
         assertEquals(table.get("b"), "test-value-2");
      }
      state.reset();
      
      System.err.println(DISPLAY[--count]);
      assertEquals(table.get("a"), "test-value-1");
      assertEquals(table.get("b"), "test-value-2");
      
      state.mark(true);
      {
         System.err.println(DISPLAY[++count]);
         table.add("c", "test-value-3");
         table.add("d", "test-value-4");
         assertEquals(table.get("a"), "test-value-1");
         assertEquals(table.get("b"), "test-value-2");
         assertEquals(table.get("c"), "test-value-3");
         assertEquals(table.get("d"), "test-value-4");
      }
      state.reset();
      
      System.err.println(DISPLAY[--count]);
      assertEquals(table.get("a"), "test-value-1");
      assertEquals(table.get("b"), "test-value-2");
      assertNull(table.get("c"));
      assertNull(table.get("d"));
      
      state.mark(false);
      {
         System.err.println(DISPLAY[++count]);
         assertNull(table.get("a"));
         assertNull(table.get("b"));
         assertNull(table.get("c"));
         assertNull(table.get("d"));
         table.add("c", "test-value-5");
         table.add("d", "test-value-6");
         assertNull(table.get("a"));
         assertNull(table.get("b"));
         assertEquals(table.get("c"), "test-value-5");
         assertEquals(table.get("d"), "test-value-6");
      }
      state.reset();
      
      System.err.println(DISPLAY[--count]);
      assertEquals(table.get("a"), "test-value-1");
      assertEquals(table.get("b"), "test-value-2");
      assertNull(table.get("c"));
      assertNull(table.get("d"));
      
      state.mark(true);
      {
         System.err.println(DISPLAY[++count]);
         table.add("c", "test-value-7");
         table.add("d", "test-value-8");
         assertEquals(table.get("a"), "test-value-1");
         assertEquals(table.get("b"), "test-value-2");
         assertEquals(table.get("c"), "test-value-7");
         assertEquals(table.get("d"), "test-value-8");
      
         state.mark(false);
         {
            System.err.println(DISPLAY[++count]);
            table.add("a", "test-value-9");
            table.add("b", "test-value-10");
            assertEquals(table.get("a"), "test-value-9");
            assertEquals(table.get("b"), "test-value-10");
            assertNull(table.get("c"));
            assertNull(table.get("d"));
         }
         state.reset();
      
         System.err.println(DISPLAY[--count]);
         assertEquals(table.get("a"), "test-value-1");
         assertEquals(table.get("b"), "test-value-2");
         assertEquals(table.get("c"), "test-value-7");
         assertEquals(table.get("d"), "test-value-8");
      }
      state.reset();
      
      System.err.println(DISPLAY[--count]);
      assertEquals(table.get("a"), "test-value-1");
      assertEquals(table.get("b"), "test-value-2");
      assertNull(table.get("c"));
      assertNull(table.get("d"));
   }
   
   private static class StateTable {
      
      private final State2 state;
      public StateTable(State2 state){
         this.state = state;
      }
      
      public Object get(String name){
         Address address = state.address(name);
         if(address == null){
            return null;
         }
         int index = address.getIndex();
         if(index <0){
            return null;
         }
         return state.get(address).getValue();
      }
      
      public void set(String name, Object value){
         Address address = state.address(name);
         if(address == null){
            throw new IllegalArgumentException("Cannot set " +name);
         }
         state.get(address).setValue(value);
      }
      
      public void add(String name, Object value){
         state.add(name, ValueType.getReference(value));
      }
   }
}
