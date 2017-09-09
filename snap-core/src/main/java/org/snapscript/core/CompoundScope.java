package org.snapscript.core;

public class CompoundScope implements Scope {
   
   private final Index index;
   private final Table table;
   private final State state;
   private final Scope outer;
   
   public CompoundScope(Scope inner, Scope outer) {
      this.state = new MapState(inner);  
      this.table = new ArrayTable();
      this.index = new StackIndex();
      this.outer = outer;
   } 
  
   @Override
   public Scope getStack() {
      throw new InternalStateException("Stack already created");
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
   public Index getIndex(){
      return index;
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