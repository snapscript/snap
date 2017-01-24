package org.snapscript.common;

public class SparseArray<T> {

   private final Object[][] segments;
   private final int block;

   public SparseArray(int length) {
      this(length, 32); // compute better default distribution
   }
   
   public SparseArray(int length, int block) {
      this.segments = new Object[length / block + 1][];
      this.block = block;
   }
   
   public void set(int index, T value) {
      int section = index /block;
      Object[] segment = segments[section];
      
      if(segment == null) {
         segment = segments[section] = new Object[block];
      }
      segment[index % block] = value;
   }
   
   public T get(int index) {
      int section = index /block;
      Object[] segment = segments[section];
      
      if(segment != null) {
         return (T)segment[index % block];
      }
      return null;
   }
   
   public void remove(int index) {
      int section = index /block;
      Object[] segment = segments[section];
      
      if(segment != null) {
         segment[index % block] = null;
      }
   }
}
