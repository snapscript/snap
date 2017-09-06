package org.snapscript.core;


public class LocalScope implements Scope {
   
   private final Counter counter;
   private final State state;
   private final Scope inner;
   private final Scope outer;
   private final Model model;
   
   public LocalScope(Model model, Scope inner, Scope outer) {
      this.state = new LocalState(inner);
      this.counter = new Counter();
      this.inner = inner;
      this.outer = outer;
      this.model = model;
   }

   @Override
   public Scope getInner() {
      return new CompoundScope(model, this, outer);
   }
   
   @Override
   public Scope getOuter() {
      return outer;
   }
   
   @Override
   public Type getHandle() {
      return inner.getType();
   }

   @Override
   public Type getType() {
      return inner.getType();
   }

   @Override
   public Module getModule() {
      return inner.getModule();
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
   public String toString() {
      return String.valueOf(state);
   }
}