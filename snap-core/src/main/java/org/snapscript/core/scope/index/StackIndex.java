package org.snapscript.core.scope.index;

import static org.snapscript.core.scope.index.AddressType.LOCAL;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.snapscript.common.ArrayStack;
import org.snapscript.common.CompoundIterator;
import org.snapscript.common.EmptyIterator;
import org.snapscript.common.Stack;
import org.snapscript.core.scope.Scope;

public class StackIndex implements ScopeIndex {

   private IndexState state;
   private Scope scope;

   public StackIndex(Scope scope) {
      this.scope = scope;
   }

   @Override
   public Iterator<Address> iterator() {
      if (state == null) {
         return new EmptyIterator<Address>();
      }
      return state.iterator();
   }

   @Override
   public Address get(String name) {
      if (state == null) {
         state = new IndexState(scope);
      }
      return state.get(name);
   }

   @Override
   public Address index(String name) {
      if (state == null) {
         state = new IndexState(scope);
      }
      return state.index(name);
   }

   @Override
   public boolean contains(String name) {
      if (state == null) {
         state = new IndexState(scope);
      }
      return state.contains(name);
   }

   @Override
   public void reset(int value) {
      if (state != null) {
         state.reset(value);
      }
   }

   @Override
   public int size() {
      if (state != null) {
         return state.size();
      }
      return 0;
   }

   @Override
   public String toString() {
      return String.valueOf(state);
   }

   private static class IndexState {

      private final Map<String, Address> externals;
      private final Map<String, Address> locals;
      private final AddressResolver resolver;
      private final Stack<String> stack;

      public IndexState(Scope scope) {
         this.resolver = new AddressResolver(scope);
         this.externals = new LinkedHashMap<String, Address>();
         this.locals = new LinkedHashMap<String, Address>();
         this.stack = new ArrayStack<String>(0); // do not use default
      }

      public Iterator<Address> iterator() {
         Iterator<Address> local = locals.values().iterator();
         Iterator<Address> reference = externals.values().iterator();

         return new CompoundIterator(local, reference);
      }

      public Address get(String name) {
         Address address = locals.get(name);

         if (address == null) {
            address = externals.get(name);
         }
         if (address == null) {
            int count = externals.size();
            Address external = resolver.resolve(name, count);

            if (external != null) {
               externals.put(name, external);
               return external;
            }
         }
         return address;
      }

      public Address index(String name) {
         Address address = locals.get(name);

         if (address != null) {
            throw new IllegalStateException("Duplicate variable '" + name + "' in scope");
         }
         int size = locals.size();
         Address local = LOCAL.getAddress(name, size);

         if(name != null) {
            locals.put(name, local);
            stack.push(name);
         }
         return local;
      }

      public boolean contains(String name) {
         return locals.containsKey(name);
      }

      public void reset(int index) {
         int size = locals.size();

         for (int i = size; i > index; i--) {
            String name = stack.pop();
            locals.remove(name);
         }
      }

      public int size() {
         return locals.size();
      }

      public String toString() {
         return String.valueOf(locals);
      }
   }
}