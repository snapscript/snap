package org.snapscript.core.store;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

public class RemoteStore implements Store {

   private final RemoteLocation location;
   private final URI root;
   
   public RemoteStore(URI root) {
      this.location = new RemoteLocation(root);
      this.root = root;
   }
   
   @Override
   public InputStream getInputStream(String path) {  
      try {
         URL resource = location.createRelative(path);
         URLConnection connection = resource.openConnection();
         
         return connection.getInputStream();
      } catch(Exception e) {
         throw new StoreException("Could not load resource '" + path + "' from '" + root + "'", e);
      }
   }

   @Override
   public OutputStream getOutputStream(String path) {
      try {
         URL resource = location.createRelative(path);
         URLConnection connection = resource.openConnection();
         
         connection.setDoOutput(true);
         
         return connection.getOutputStream();
      } catch(Exception e) {
         throw new StoreException("Could not load resource '" + path + "' from '" + root + "'", e);
      }
   }
}
