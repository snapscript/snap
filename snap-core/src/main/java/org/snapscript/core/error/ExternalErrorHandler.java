package org.snapscript.core.error;

import org.snapscript.core.InternalException;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;

public class ExternalErrorHandler {

   public ExternalErrorHandler() {
      super();
   }

   public Result throwExternal(Scope scope, Object cause) throws Exception {
      if(Exception.class.isInstance(cause)) {
         throw (Exception)cause;
      }
      if(Throwable.class.isInstance(cause)) {
         throw new InternalException((Throwable)cause);
      }
      throw new InternalException(String.valueOf(cause));
   }
}
