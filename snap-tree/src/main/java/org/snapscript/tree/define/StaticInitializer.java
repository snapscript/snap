package org.snapscript.tree.define;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.define.Initializer;

public abstract class StaticInitializer extends Initializer {

   private final AtomicReference<Result> reference;
   private final AtomicBoolean compile;
 
   protected StaticInitializer() {
      this.reference = new AtomicReference<Result>();
      this.compile = new AtomicBoolean();
   }

   @Override
   public Result compile(Scope scope, Type type) throws Exception { 
      Result result = reference.get();
      
      if(result == null) {
         Module module = type.getModule();
         Context context = module.getContext();
         
         synchronized(context) { // static lock to force wait
            Result value = ResultType.getNormal();
            
            if(compile.compareAndSet(false, true)) { // only do it once
               compile(type);
            }
            reference.set(value); // avoid locking again
         }
         return reference.get();
      }
      return result; // result of compilation
   }
   
   protected abstract Result compile(Type type) throws Exception; 
}
