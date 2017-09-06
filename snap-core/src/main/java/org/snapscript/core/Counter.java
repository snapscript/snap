package org.snapscript.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Bug("is this needed")
public class Counter implements Iterable<String> {
   
   private final Map<String, Integer> locals;

   public Counter() {
      this.locals = new HashMap<String, Integer>();
   }
   
   @Override
   public Iterator<String> iterator(){
      return locals.keySet().iterator();
   }
   
   public int get(String name) {
      Integer index = locals.get(name);
      
      if(index != null){
         return index;
      }
      return -1;
   }
   
   public int add(String name) {
      Integer index = locals.get(name);
      
      if(index == null) {
         int size = locals.size();
         
         if(name != null) {
            locals.put(name, size);
         }
         return size;
      }
      return index;
   }

   public int size(){
      return locals.size();
   }
   
   @Override
   public String toString() {
      return String.valueOf(locals);
   }
}