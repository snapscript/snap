package org.snapscript.core;

public class CompoundScope implements Scope {
   
   private final Counter counter;
   private final Table table;
   private final State state;
   private final Scope outer;
   
   public CompoundScope(Scope inner, Scope outer) {
      this.state = new MapState(inner);  
      this.counter = new MapCounter();
      this.table = new ArrayTable();
      this.outer = outer;
   } 
  
   @Override
   public Scope getStack() {
      throw new IllegalStateException("stack inner");
   }  
   
   @Override
   public Scope getScope() {
      return outer;
   }  
   
   @Override
   public Type getHandle() {
      return outer.getType();
   }
   
   @Override
   public Type getType() {
      return outer.getType();
   }
  
   @Override
   public Module getModule() {
      return outer.getModule();
   } 
   
   @Override
   public Counter getCounter(){
      return counter;
   }
   
   @Override
   public Table getTable(){
      return table;
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