package org.snapscript.core;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

public class ProgramScopeTest extends TestCase {
   
   private static final int STACK = 1;
   private static final int MODULE = 2;

   public void testState() throws Exception {
      Map<String, Object> map = new HashMap<String, Object>();
      Model model = new MapModel(map);
      Stack stack = new AddressState(STACK);
      Scope inner = new MockScope(MODULE);
      ProgramScope scope = new ProgramScope(null, inner, stack, model, MODULE);
      
      State state = scope.getState();
      
      map.put("x", "X");
      map.put("y", "Y");
      
      assertEquals(state.add("a", ValueType.getReference("A")).getSource(), MODULE);
      assertEquals(state.add("b", ValueType.getReference("B")).getSource(), MODULE);
      
      Address address1 = state.address("a");
      Address address2 = state.address("b");
      Address address3 = state.address("x");
      Address address4 = state.address("y");
      
      assertEquals(state.get(address1).getValue(), "A");
      assertEquals(state.get(address2).getValue(), "B");
      assertEquals(state.get(address3).getValue(), "X");
      assertEquals(state.get(address4).getValue(), "Y");
   }
   
   private static class MockScope implements Scope {
      
      private final State state;
      
      public MockScope(int key) {
         this.state = new AddressState(key);
      }

      @Override
      public Type getHandle() {
         return null;
      }

      @Override
      public Type getType() {
         return null;
      }

      @Override
      public Scope getInner() {
         return null;
      }

      @Override
      public Scope getObject() {
         return null;
      }

      @Override
      public Module getModule() {
         return null;
      }

      @Override
      public State getState() {
         return state;
      }

      @Override
      public Stack getStack() {
         return null;
      }

      @Override
      public Model getModel() {
         return null;
      }
      
   }
}
