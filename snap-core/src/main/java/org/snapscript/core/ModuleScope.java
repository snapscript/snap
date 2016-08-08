package org.snapscript.core;

public class ModuleScope implements Scope {
   
   private final Module module;
   private final State state;
   
   public ModuleScope(Module module) {
      this.state = new MapState(null);
      this.module = module;
   }
   
   @Override
   public Scope getInner() {
      return new CompoundScope(null, this, this);
   } 
   
   @Override
   public Scope getOuter() {
      return this;
   }

   @Override
   public State getState() {
      return state;
   }
   
   @Override
   public Model getModel() {
      return null;
   }
   
   @Override
   public Type getType() {
      return null;
   }  

   @Override
   public Module getModule() {
      return module;
   }
   
   @Override
   public String toString() {
      return String.valueOf(state);
   }
}
