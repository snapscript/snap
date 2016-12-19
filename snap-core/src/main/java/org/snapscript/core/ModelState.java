package org.snapscript.core;

import static org.snapscript.core.StateType.MODEL;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ModelState implements State {
   
   private final List<String> empty;
   private final Model model;
   
   public ModelState(Model model) {
      this.empty = Collections.emptyList();
      this.model = model;
   }

   @Override
   public Iterator<String> iterator() {
      return empty.iterator();
   }

   @Override
   public boolean contains(String name) {
      Object value = model.getAttribute(name);
      
      if(value == null) {
         return false;
      }
      return true;
   }

   @Override
   public Address address(String name) {
      Object value = model.getAttribute(name);
      
      if(value == null) {
         return new Address(name, MODEL.mask, -1);
      }
      return new Address(name, MODEL.mask, 0);
   }

   @Override
   public Value get(Address address) {
      String name = address.getName();
      Object value = model.getAttribute(name);
      
      if(value != null) {
         return ValueType.getConstant(value);
      }
      return null;
   }

   @Override
   public Value get(String name) {
      Object value = model.getAttribute(name);
      
      if(value != null) {
         return ValueType.getConstant(value);
      }
      return null;
   }

   @Override
   public Address add(String name, Value value) {
      throw new InternalStateException("Illegal modification of model");
   }

   @Override
   public void set(Address address, Value value) {
      throw new InternalStateException("Illegal modification of model");
   }
}
