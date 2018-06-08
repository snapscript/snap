package org.snapscript.common.store;

import java.io.InputStream;
import java.io.OutputStream;

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
         int index = path.indexOf('/');
         int length = path.length();
         
         if(index == 0 && length > 0) {
            String relative = path.substring(1);
            int remainder = relative.length();
            
            if(remainder > 0) {
               return getInputStream(relative);
            }
         }
         throw new NotFoundException("Could not find '" + path + "'");
      }  
      return source;
   }
   
   @Override
   public OutputStream getOutputStream(String path) {
      return null;
   }
}