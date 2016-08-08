package org.snapscript.core.store;

import java.io.InputStream;
import java.io.OutputStream;

import org.snapscript.core.InternalArgumentException;

public class ClassPathStore implements Store {

   public ClassPathStore() {
      super();
   }
   
   @Override
   public InputStream getInputStream(String path) {
      Thread thread = Thread.currentThread();
      ClassLoader loader = thread.getContextClassLoader();
      InputStream source = loader.getResourceAsStream(path);
      
      if(source == null) {
         throw new InternalArgumentException("Could not find '" + path + "'");
      }  
      return source;
   }
   
   @Override
   public OutputStream getOutputStream(String path) {
      return null;
   }
}
