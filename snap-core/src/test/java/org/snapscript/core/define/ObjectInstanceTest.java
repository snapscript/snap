package org.snapscript.core.define;

import static org.snapscript.core.StateType.MODULE;
import static org.snapscript.core.StateType.OBJECT;
import static org.snapscript.core.StateType.STACK;
import static org.snapscript.core.StateType.SUPER;

import java.io.InputStream;
import java.util.List;

import junit.framework.TestCase;

import org.snapscript.core.Address;
import org.snapscript.core.AddressState;
import org.snapscript.core.CompoundState;
import org.snapscript.core.Context;
import org.snapscript.core.EmptyModel;
import org.snapscript.core.Model;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Stack;
import org.snapscript.core.State;
import org.snapscript.core.Type;
import org.snapscript.core.ValueType;
import org.snapscript.core.annotation.Annotation;
import org.snapscript.core.function.Function;
import org.snapscript.core.link.ImportManager;

public class ObjectInstanceTest extends TestCase {
   
   public void testInstance() throws Exception {
      //   public ObjectInstance(State stack, Module module, Model model, Instance base, Type type, int key) {
      Stack stack = new AddressState(STACK.mask);
      State state = new AddressState(SUPER.mask);
      Scope scope = new MockScope(stack, MODULE.mask);
      Module module = new MockModule(scope);
      Model model = new EmptyModel();
      Instance base = new MockInstance(state);
      ObjectInstance instance = new ObjectInstance(stack, module, model, base, null, OBJECT.mask);
      
      assertEquals(instance.getState().add("this.a", ValueType.getConstant("THIS-A")).getSource(), OBJECT.mask);
      assertEquals(instance.getState().add("this.b", ValueType.getConstant("THIS-B")).getSource(), OBJECT.mask);
      
      assertEquals(base.getState().add("super.a", ValueType.getConstant("SUPER-A")).getSource(), SUPER.mask);
      assertEquals(base.getState().add("super.b", ValueType.getConstant("SUPER-B")).getSource(), SUPER.mask);
      
      assertEquals(stack.add("a", ValueType.getConstant("A")).getSource(), STACK.mask);
      assertEquals(stack.add("b", ValueType.getConstant("B")).getSource(), STACK.mask);
      
      Address address1 = instance.getState().address("this.a");
      Address address2 = instance.getState().address("this.b");
      Address address3 = instance.getState().address("super.a");
      Address address4 = instance.getState().address("super.b");
      Address address5 = instance.getState().address("a");
      Address address6 = instance.getState().address("b");
      
      assertEquals(address1.getSource(), OBJECT.mask);
      assertEquals(address2.getSource(), OBJECT.mask);
      assertEquals(address3.getSource(), SUPER.mask);
      assertEquals(address4.getSource(), SUPER.mask);
      assertEquals(address5.getSource(), STACK.mask);
      assertEquals(address6.getSource(), STACK.mask);
      
      assertEquals(instance.getState().get(address1).getValue(), "THIS-A");
      assertEquals(instance.getState().get(address2).getValue(), "THIS-B");
      assertEquals(instance.getState().get(address3).getValue(), "SUPER-A");
      assertEquals(instance.getState().get(address4).getValue(), "SUPER-B");
      assertEquals(instance.getState().get(address5).getValue(), "A");
      assertEquals(instance.getState().get(address6).getValue(), "B");
   }
   
   private static class MockInstance implements Instance {
      
      private final State state;
      
      public MockInstance(State state) {
         this.state = state;
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
         return null;
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
   
   private static class MockScope implements Scope {
      
      private final Stack stack;
      private final State state;
      
      public MockScope(Stack stack, int key) {
         this.state = new CompoundState(stack, key);
         this.stack = stack;
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
         return null;
      }

      @Override
      public Stack getStack() {
         return stack;
      }

      @Override
      public Model getModel() {
         return null;
      }
      
   }
   
   private static class MockModule implements Module {
      
      private final Scope scope;
      
      public MockModule(Scope scope) {
         this.scope = scope;
      }

      @Override
      public Scope getScope() {
         return scope;
      }

      @Override
      public Context getContext() {
         return null;
      }

      @Override
      public ImportManager getManager() {
         return null;
      }

      @Override
      public Type getType(Class type) {
         return null;
      }

      @Override
      public Type getType(String name) {
         return null;
      }

      @Override
      public Type addType(String name) {
         return null;
      }

      @Override
      public Module getModule(String module) {
         return null;
      }

      @Override
      public InputStream getResource(String path) {
         return null;
      }

      @Override
      public List<Annotation> getAnnotations() {
         return null;
      }

      @Override
      public List<Function> getFunctions() {
         return null;
      }

      @Override
      public List<Type> getTypes() {
         return null;
      }

      @Override
      public String getPath() {
         return null;
      }

      @Override
      public String getName() {
         return null;
      }

      @Override
      public int getOrder() {
         return 0;
      }
      
   }
   
}
