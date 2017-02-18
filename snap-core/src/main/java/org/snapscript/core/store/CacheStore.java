package org.snapscript.core.store;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

import org.snapscript.common.Cache;
import org.snapscript.common.LeastRecentlyUsedSet;
import org.snapscript.common.SoftCache;

public class CacheStore implements Store{

   private final Cache<String, byte[]> cache;
   private final Set<String> failures;
   private final StoreReader reader;
   private final Store store;
   
   public CacheStore(Store store) {
      this(store, 100);
   }
   
   public CacheStore(Store store, int capacity) {
      this(store, capacity, 8192);
   }
   
   public CacheStore(Store store, int capacity, int read) {
      this.failures = new LeastRecentlyUsedSet<String>(capacity);
      this.cache = new SoftCache<String, byte[]>(capacity);
      this.reader = new StoreReader(store, read);
      this.store = store;
   }

   @Override
   public InputStream getInputStream(String path) {
      if(!failures.contains(path)) {
         byte[] resource = cache.fetch(path);
         
         if(resource == null) {
            try {
               resource = reader.getBytes(path);
               cache.cache(path, resource);
            } catch(NotFoundException cause) {
               failures.add(path);
               throw cause;
            }
         }
         if(resource == null) {
            throw new NotFoundException("Could not find '" + path + "'");
         }
         return new ByteArrayInputStream(resource);
      }
      return null;
   }

   @Override
   public OutputStream getOutputStream(String path) {
      return store.getOutputStream(path);
   }
}
