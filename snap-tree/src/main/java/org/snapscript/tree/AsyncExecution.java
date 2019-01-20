package org.snapscript.tree;

import java.util.Iterator;

import org.snapscript.common.Consumer;
import org.snapscript.core.Bug;
import org.snapscript.core.Context;
import org.snapscript.core.Execution;
import org.snapscript.core.ModifierType;
import org.snapscript.core.Promise;
import org.snapscript.core.TaskScheduler;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.module.Module;
import org.snapscript.core.result.Result;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.yield.Yield;

public class AsyncExecution extends Execution {

   private final Execution execution;
   private final int modifiers;

   public AsyncExecution(Execution execution, int modifiers) {
      this.execution = execution;
      this.modifiers = modifiers;
   }

   @Override
   public Result execute(Scope scope) throws Exception {
      Result result = execution.execute(scope);

      if(result.isAwait()) {
         AsyncConsumer consumer = new AsyncConsumer(result);

         if(!ModifierType.isAsync(modifiers)) {
            throw new InternalStateException("Function is not asynchronous");
         }
         Module module = scope.getModule();
         Context context = module.getContext();
         TaskScheduler scheduler = context.getScheduler();
         Promise promise = scheduler.schedule(consumer);

         return Result.getNormal(promise);
      }
      return result;
   }

   private static class AsyncConsumer implements Consumer<Object, Object> {

      private final Result result;

      public AsyncConsumer(Result result){
         this.result = result;
      }

      @Bug
      @Override
      public Object consume(Object scope) {
         Yield yield = result.getValue();
         Iterator<Object> iterator = yield.iterator();

         if(iterator.hasNext()) {
            return iterator.next();
         }
         return null;
      }
   }
}
