package org.snapscript.core.scope.index;

import static org.snapscript.core.scope.index.AddressType.LOCAL;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.snapscript.common.ArrayStack;
import org.snapscript.common.CompoundIterator;
import org.snapscript.common.Stack;
import org.snapscript.core.scope.Scope;

public class StackIndex implements ScopeIndex {

   private final Map<String, Address> externals;
   private final Map<String, Address> locals;
   private final AddressResolver resolver;
   private final Stack<String> stack;
   
   public StackIndex(Scope scope) {
      this.resolver = new AddressResolver(scope);
      this.externals = new LinkedHashMap<String, Address>();
      this.locals = new LinkedHashMap<String, Address>();
      this.stack = new ArrayStack<String>(0); // do not use default
   }
   
   @Override
   public Iterator<Address> iterator(){
      Iterator<Address> local = locals.values().iterator();
      Iterator<Address> reference = externals.values().iterator();
      
      return new CompoundIterator(local, reference);
   }
   
   @Override
   public Address get(String name) {
      Address address = locals.get(name);
      
      if(address == null) {
         address = externals.get(name);
      }
      if(address == null) {
         int count = externals.size();
         Address created = resolver.resolve(name, count);
         
         if(created != null) {
            externals.put(name, created);
            return created;
         }
      }
      return address;
   }
   
   @Override
   public Address index(String name) {
      Address address = locals.get(name);
      
      if(address != null) {
         throw new IllegalStateException("Duplicate variable '" + name + "' in scope");
      }
      int size = locals.size();
      Address local = LOCAL.getAddress(name, size);
      
      locals.put(name, local);
      stack.push(name);
      
      return local;
   }
   
   @Override
   public boolean contains(String name) {
      return locals.containsKey(name);
   }
   
   @Override
   public void reset(int index) {
      int size = locals.size();
      
      for(int i = size; i > index; i--) {
         String name = stack.pop();
         locals.remove(name);
      }
   }

   @Override
   public int size(){
      return locals.size();
   }
   
   @Override
   public String toString() {
      return String.valueOf(locals);
   }
}