package org.snapscript.core;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.snapscript.core.store.CacheStore;
import org.snapscript.core.store.NotFoundException;
import org.snapscript.core.store.Store;
import org.snapscript.core.store.StoreException;

public class StoreManager implements ResourceManager {

   private final Store store;
   private final int read;
   
   public StoreManager(Store store) {
      this(store, 100);
   }
   
   public StoreManager(Store store, int capacity) {
      this(store, capacity, 8192);
   }
   
   public StoreManager(Store store, int capacity, int read) {
      this.store = new CacheStore(store, capacity, read);
      this.read = read;
   }
   
   @Override
   public InputStream getInputStream(String path) {
      try {
         return store.getInputStream(path);
      }catch(NotFoundException e) {
         return null;
      }
   }

   @Override
   public byte[] getBytes(String path) {
      InputStream source = getInputStream(path);
      
      try {
         if(source != null) {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream(read);
            byte[] array = new byte[read];
            int count = 0;
            
            while((count = source.read(array)) != -1) {
               buffer.write(array, 0, count);
            }
            return buffer.toByteArray();
         }
      } catch(Exception e) {
         throw new StoreException("Could not read resource '" + path + "'", e);
      }
      return null;
   }

   @Override
   public String getString(String path) {
      InputStream source = getInputStream(path);
      
      try {
         if(source != null) {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream(read);
            byte[] array = new byte[read];
            int count = 0;
            
            while((count = source.read(array)) != -1) {
               buffer.write(array, 0, count);
            }
            return buffer.toString("UTF-8");
         }
      } catch(Exception e) {
         throw new StoreException("Could not read resource '" + path + "'", e);
      }
      return null;
   }

}
