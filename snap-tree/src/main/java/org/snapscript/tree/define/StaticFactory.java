package org.snapscript.tree.define;

import java.util.concurrent.atomic.AtomicBoolean;

import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;

public abstract class StaticFactory extends TypeFactory {

   private final AtomicBoolean allocate;
   private final AtomicBoolean compile;
   
   protected StaticFactory() {
      this.allocate = new AtomicBoolean();
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
   
   @Override
   public void allocate(Scope scope, Type type) throws Exception { 
      if(!allocate.get()) {
         Module module = type.getModule();
         Context context = module.getContext();
         
         synchronized(context) { // static lock to force wait
            if(allocate.compareAndSet(false, true)) { // only do it once
               allocate(type);
            }
         }
      }
   }

   protected void compile(Type type) throws Exception {}
   protected void allocate(Type type) throws Exception {}
}