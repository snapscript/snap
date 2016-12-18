package org.snapscript.core;

public class ProgramScope implements Scope {
   
   private final Module module;
   private final State stack;
   private final State state;
   private final Model model;
   
   public ProgramScope(Module module, Scope scope, State stack, Model model) {
      this.state = new ProgramState(scope, model);
      this.module = module;
      this.stack = stack;
      this.model = model;
      
      if(stack == null) {
         throw new IllegalStateException("Stack must not be null");
      }
   }
   
   @Override
   public Scope getInner() {
      return new CompoundScope(stack, model, this, this);
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
