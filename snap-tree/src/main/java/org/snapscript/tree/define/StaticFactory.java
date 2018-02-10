package org.snapscript.tree.define;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;

public abstract class StaticFactory extends TypeFactory {

   private final AtomicBoolean compile;
 
   protected StaticFactory() {
      this.compile = new AtomicBoolean();
   }

   @Override
   public void compile(Scope scope, Type type) throws Exception { 
      if(!compile.get()) {
         Module module = type.getModule();
         Context context = module.getContext();
         
         synchronized(context) { // static lock to force wait
            if(compile.compareAndSet(false, true)) { // only do it once
               compile(type);
            }
         }
      }
   }
   
   protected abstract void compile(Type type) throws Exception; 
}