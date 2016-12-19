package org.snapscript.core;

import static org.snapscript.core.StateType.MODULE;


public class ModuleScope implements Scope {
   
   private final Module module;
   private final State state;
   private final Stack stack;
   
   public ModuleScope(Module module, Stack stack, int order) {
      this.state = new ModuleState(stack, MODULE.mask | order);
      this.module = module;
      this.stack = stack;

   }
   
   @Override
   public Scope getInner() {
      return new CompoundScope(stack, null, this, this);
   } 
   
   @Override
   public Stack getStack(){
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
