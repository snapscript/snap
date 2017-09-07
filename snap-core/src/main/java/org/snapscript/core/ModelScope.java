package org.snapscript.core;

public class ModelScope implements Scope {
   
   private final Counter counter;
   private final Table table;
   private final Module module;
   private final State state;
   
   public ModelScope(Model model, Module module) {
      this.state = new ModelState(model);
      this.counter = new MapCounter();
      this.table = new ArrayTable();
      this.module = module;
   }
   
   @Override
   public Scope getStack() {
      return new CompoundScope(this, this);
   } 
   
   @Override
   public Scope getScope() {
      return this;
   } 
   
   @Override
   public Counter getCounter(){
      return counter;
   }
   
   @Override
   public Table getTable() {
      return table;
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