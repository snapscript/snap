package org.snapscript.core.link;

import java.util.concurrent.FutureTask;

import org.snapscript.core.Execution;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Path;
import org.snapscript.core.Scope;
import org.snapscript.core.result.Result;

public class FutureExecution extends Execution {
   
   private final FutureTask<Execution> result;
   private final Path path;
   
   public FutureExecution(FutureTask<Execution> result, Path path) {
      this.result = result;
      this.path = path;
   }

   @Override
   public Result execute(Scope scope) throws Exception {
      Execution execution = result.get();
      
      if(execution == null) {
         throw new InternalStateException("Could not execute '" + path + "'");
      }
      return execution.execute(scope);
   }

}
