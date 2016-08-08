package org.snapscript.core.store;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileStore implements Store {

   private final ClassPathStore store;
   private final File root;
   
   public FileStore(File root) {
      this.store = new ClassPathStore();
      this.root = root;
   }
   
   @Override
   public InputStream getInputStream(String path) {
      try {
         File resource = new File(root, path);
         
         if(resource.exists()) {
            return new FileInputStream(resource);
         }
      } catch(Exception e) {
         throw new StoreException("Could not load resource '" + path + "'", e);
      }
      return store.getInputStream(path);
   }

   @Override
   public OutputStream getOutputStream(String path) {
      try {
         File resource = new File(root, path);
         
         if(resource.exists()) {
            resource.delete();
         }
         File parent = resource.getParentFile();
         
         if(parent.exists()) {
            parent.mkdirs();
         }
         return new FileOutputStream(resource);
      } catch(Exception e) {
         throw new StoreException("Could not load resource '" + path + "'", e);
      }
   }
}
