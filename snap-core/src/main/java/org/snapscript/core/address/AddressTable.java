package org.snapscript.core.address;

import java.util.Iterator;

import org.snapscript.core.InternalStateException;

public class AddressTable {
   
   private Object[] values;
   private Object[] names;
   private Object key;
   private int count;
   private int start; 
   
   public AddressTable(Object key) {
      this(key, 16);
   }
   
   public AddressTable(Object key, int size) {
      this.values = new Object[size];
      this.names = new Object[size];
      this.key = key;
   }
   
   public Iterator<String> iterator(int index){
      return new NameIterator(index); // provide the names in scope
   }
   
   public int add(String name, Object value) {
      if(count +2 > values.length) {
         expand(count * 2);
      }
      names[count] = name;
      values[count] = value;
      return count++;
      
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
      
      public int index;
      
      public NameIterator(int index) {
         this.index = index;
      }
      
      @Override
      public boolean hasNext() {
         if(index >= 0) {
            return true;
         }
         return false;
      }
      
      @Override
      public String next() {
         if(index >= 0) {
            return (String)names[index--];
         }
         return null;
      }
      
      @Override
      public void remove() {
         throw new UnsupportedOperationException("Remove not supported");
      }
   }
}