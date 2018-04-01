package org.snapscript.core.scope.index;

import java.util.Iterator;

import org.snapscript.common.ArrayStack;
import org.snapscript.common.Cache;
import org.snapscript.common.HashCache;
import org.snapscript.common.Stack;

public class StackIndex implements Index {
   
   private final Cache<String, Integer> locals;
   private final Stack<String> stack;

   public StackIndex() {
      this.locals = new HashCache<String, Integer>();
      this.stack = new ArrayStack<String>(0); // do not use default
   }
   
   @Override
   public Iterator<String> iterator(){
      return locals.keySet().iterator();
   }
   
   @Override
   public int get(String name) {
      Integer index = locals.fetch(name);
      
      if(index != null){
         return index;
      }
      return -1;
   }
   
   @Override
   public int index(String name) {
      Integer index = locals.fetch(name);
      
      if(index != null) {
         throw new IllegalStateException("Duplicate variable '" + name + "' in scope");
      }
      int size = locals.size();
      
      locals.cache(name, size);
      stack.push(name);
      
      return size;
   }
   
   @Override
   public void reset(int index) {
      int size = locals.size();
      
      for(int i = size; i > index; i--) {
         String name = stack.pop();
         locals.take(name);
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