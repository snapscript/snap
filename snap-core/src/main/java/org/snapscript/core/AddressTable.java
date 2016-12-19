package org.snapscript.core;

import java.util.Iterator;

public class AddressTable implements Iterable<String> {
   
   private Object[] values;
   private Object[] names;
   private int key;
   private int count;
   private int start; 
   
   public AddressTable(int key) {
      this(key, 16);
   }
   
   public AddressTable(int key, int size) {
      this.values = new Object[size];
      this.names = new Object[size];
      this.key = key;
   }
   
   @Override
   public Iterator<String> iterator(){
      return new NameIterator(start, count); // provide the names in scope
   }
   
   public boolean contains(String name) {
      for(int i = count - 1; i >= start; i--) {
         if(names[i].equals(name)) {
            return true;
         }
      }
      return false;
   }
   
   @Bug("Is this going to be too slow??? might be required tho as double variables would not be good")
   public Address add(String name, Object value) {
      if(count +2 > values.length) {
         expand(count * 2);
      }
//      for(int i = count - 1; i >= start; i--) {
//         if(names[i].equals(name)) {
//            throw new InternalStateException("Address already exists for '" + name + "'");
//         }
//      }
      names[count] = name;
      values[count] = value;
      
      return new Address(name, key, count++);
      
   }
   
   public void set(Address address, Object value) {
      String name = address.getName();
      int index = address.getIndex();
      
      if(index < 0 || index + start >= count) {
         throw new InternalStateException("Address '" + index +"' invalid for '" + name + "'");
      }
      values[start + index] = value;
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
   
   public Object get(String name) {
      Address address = address(name);
      int index = address.getIndex();
      
      if(index >= 0) {
         return values[start +index];
      }
      return null;
   }
   
   public Object get(Address address) {
      String name = address.getName();
      int index = address.getIndex();
      
      if(index < 0 || index + start >= count) {
         throw new InternalStateException("Address '" + index +"' invalid for '" + name + "'");
      }
      return values[start +index];
   }
   
   public Address address(String name) {
      for(int i = count - 1; i >= start; i--) {
         if(names[i].equals(name)) {
            return new Address(name, key, i-start); // make it an offset from top
         }
      }
      return new Address(name, key, -1); // invalid index
   }
   
   public void reset(long position) {
      int mark = (int)(position >>> 32); // how much did it increase
      int size = (int)(position & 0xffff);
      
      for(int i = size; i < count; i++) {
         names[i] =null;
         values[i] = null;
      }
      start = mark;
      count = size;
   }
   
   public void clear() {
      for(int i = 0; i < count; i++) {
         names[i] =null;
         values[i] = null;
      }
      start = 0;
      count = 0;
   }
  
   public long mark() { // where was the start, what was the count
      long mark = start;
      
      mark <<= 32;
      mark |= count;
      start = count; // move the start on
      
      return mark;
   }
   
   public long position(){ //
      long mark = start;
      
      mark <<= 32;
      mark |= count;
      
      return mark;
   }
   
   public int size(){
      return count;
   }
   
   private class NameIterator implements Iterator<String> {
      
      private int start;
      private int count;
      
      public NameIterator(int start, int count) {
         this.start = start;
         this.count = count;
      }
      
      @Override
      public boolean hasNext() {
         if(start < count) {
            return true;
         }
         return false;
      }
      
      @Override
      public String next() {
         if(start < count) {
            return (String)names[start++];
         }
         return null;
      }
      
      @Override
      public void remove() {
         throw new UnsupportedOperationException("Remove not supported");
      }
   }
}