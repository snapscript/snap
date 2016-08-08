package org.snapscript.core.define;

import org.snapscript.core.Model;
import org.snapscript.core.Module;
import org.snapscript.core.State;
import org.snapscript.core.Type;

public class SuperInstance implements Instance {

   private final Instance scope;
   private final Module module;
   private final Model model;
   private final Type base;
   private final Type real;
   
   public SuperInstance(Module module, Model model, Instance scope, Type base, Type real) {
      this.scope = scope;
      this.module = module;
      this.model = model;
      this.base = base;
      this.real = real;
   }

   @Override
   public State getState() {
      return scope.getState();
   }

   @Override
   public Instance getInstance() {
      return scope.getInstance();
   }

   @Override
   public void setInstance(Instance instance) {
      scope.setInstance(instance);
   }

   @Override
   public Instance getInner() {
      return scope.getInner();
   }

   @Override
   public Instance getOuter() {
      return scope.getOuter();
   }
   
   public Type getSuper() {
      return base;
   }

   @Override
   public Type getType() {
      return real;
   }

   @Override
   public Module getModule() {
      return module;
   }

   @Override
   public Model getModel() {
      return model;
   }
   
   @Override
   public String toString() {
      return scope.toString();
   }
}
