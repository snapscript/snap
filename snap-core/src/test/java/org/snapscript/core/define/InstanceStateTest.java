package org.snapscript.core.define;

import junit.framework.TestCase;

import org.snapscript.core.Address;
import org.snapscript.core.AddressState;
import org.snapscript.core.Model;
import org.snapscript.core.Module;
import org.snapscript.core.Stack;
import org.snapscript.core.State;
import org.snapscript.core.Type;
import org.snapscript.core.ValueType;

public class InstanceStateTest extends TestCase {
   
   private static final int STACK = 1;
   private static final int SUPER = 2;
   private static final int OBJECT = 3;

   public void testInstanceState() throws Exception {
      System.err.println("stack="+STACK);
      System.err.println("super="+SUPER);
      System.err.println("instance="+OBJECT);
      
      Stack stack = new AddressState(STACK); // this represents the stack
      Instance base = new MockInstance(stack, SUPER); // super instance
      InstanceState state = new InstanceState(base, stack, OBJECT); // actual instance
      
      assertEquals(state.add("this.a", ValueType.getConstant("a")).getSource(), OBJECT);
      assertEquals(state.add("this.b", ValueType.getConstant("b")).getSource(), OBJECT);
      
      Address address1 = state.address("this.a");
      Address address2 = state.address("this.b");
      
      assertEquals(state.get(address1).getValue(), "a");
      assertEquals(state.get(address2).getValue(), "b");
   }
   
   private static class MockInstance implements Instance {
      
      private final Stack stack;
      private final State state;
      
      public MockInstance(Stack stack, int key) {
         this.state = new AddressState(key);
         this.stack = stack;
      }

      @Override
      public Type getType() {
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
         return stack;
      }

      @Override
      public Model getModel() {
         return null;
      }

      @Override
      public Type getHandle() {
         return null;
      }

      @Override
      public Instance getInner() {
         return null;
      }

      @Override
      public Instance getObject() {
         return null;
      }

      @Override
      public Instance getSuper() {
         return null;
      }
      
   }
}
