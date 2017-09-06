package org.snapscript.core;

public class ModelScope implements Scope {
   
   private final Counter counter;
   private final Module module;
   private final State state;
   private final Model model;
   
   public ModelScope(Model model, Module module) {
      this.state = new MapState(model);
      this.counter = new Counter();
      this.module = module;
      this.model = model;
   }
   
   @Override
   public Scope getInner() {
      return new CompoundScope(model, this, this);
   } 
   
   @Override
   public Scope getOuter() {
      return this;
   } 
   
   @Override
   public Counter getCounter(){
      return counter;
   }
   
   @Override
   public Model getModel() {
      return model;
   }

   @Override
   public State getState() {
      return state;
   }
   
   @Override
   public Type getHandle() {
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