package org.snapscript.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.snapscript.common.ArrayStack;
import org.snapscript.common.Stack;

public class StackIndex implements Index {
   
   private final Map<String, Integer> locals;
   private final Stack<String> stack;

   public StackIndex() {
      this.locals = new HashMap<String, Integer>();
      this.stack = new ArrayStack<String>(1);
   }
   
   @Override
   public Iterator<String> iterator(){
      return locals.keySet().iterator();
   }
   
   @Override
   public int get(String name) {
      Integer index = locals.get(name);
      
      if(index != null){
         return index;
      }
      return -1;
   }
   
   @Override
   public int index(String name) {
      Integer index = locals.get(name);
      
      if(index != null) {
         throw new IllegalStateException("Duplicate variable '" + name + "' in scope");
      }
      int size = locals.size();
      
      locals.put(name, size);
      stack.push(name);
      
      return size;
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