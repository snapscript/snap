
package org.snapscript.core.store;

public class NotFoundException extends StoreException {

   public NotFoundException(String message) {
      super(message);
   }
   
   public NotFoundException(String message, Throwable cause) {
      super(message, cause);
   }
}
