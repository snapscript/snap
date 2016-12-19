package org.snapscript.core.stack;

import org.snapscript.core.InternalStateException;

public class AddressTable {
   
   private Object[] values; // growable memory adddress
   private Object[] names;
   private int count;
   
   public AddressTable() {
      this(10);
   }
   
   public AddressTable(int size) {
      this.values = new Object[size];
      this.names = new String[size];
   }
   
   public int add(String name, Object value) {
      if(count +2 > values.length) {
         expand(count * 2);
      }
      names[count] = name;
      values[count] = value;
      return count++;
      
   }
   
   public void set(int index, Object value) {
      if(index < 0 || index >= count) {
         throw new InternalStateException("Address '" + index +"' out of bounds");
      }
      values[index] = value;
   }
   
   private void expand(int capacity) {
      if(capacity > values.length) {
         values = expand(values, capacity);
         names = expand(names, capacity);
      }
   }
   
   private Object[] expand(Object[] values, int capacity){
      Object[] copy = new Object[capacity];
      
      for(int i = 0; i < values.length; i++){
         copy[i] = values[i];
      }
      return copy;
   }
   
   public Object get(int index) {
      if(index < 0 || index >= count) {
         throw new InternalStateException("Address '" + index +"' out of bounds");
      }
      return values[index];
   }
   
   public int indexOf(String name) {
      for(int i = count-1; i >= 0; i--) {
         if(names[i].equals(name)) {
            return i;
         }
      }
      return -1;
   }
   
   public void reset(int index) {
      for(int i = count-1; i >= index; i--) {
         names[i] =null;
         values[i] = null;
      }
      count = index;
   }
   
   public void clear() {
      for(int i = 0; i < count; i++) {
         names[i] =null;
         values[i] = null;
      }
      count = 0;
   }
   
   public int size(){
      return count;
   }
}