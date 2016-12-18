package org.snapscript.core;


public class ModuleScope implements Scope {
   
   private final Module module;
   private final State state;
   private final State stack;
   
   public ModuleScope(Module module, State stack) {
      this.state = new ModuleState(module, stack);
      this.module = module;
      this.stack = stack;

   }
   
   @Override
   public Scope getInner() {
      return new CompoundScope(state, null, this, this);
   } 
   
   @Override
   public State getStack(){
      return stack;
   }
   
   @Override
   public Scope getObject() {
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
