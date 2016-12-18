package org.snapscript.core;

import org.snapscript.core.State;

public class TypeScope implements Scope {
   
   private final State stack;
   private final State state;
   private final Type type;
   
   public TypeScope(Type type, State stack) {
      this.state = new MapState();
      this.stack = stack;
      this.type = type;
      
      if(stack == null){
         throw new IllegalStateException("Stack must not be null");
      }
   }
   
   @Override
   public Scope getInner() {
      return new CompoundScope(stack, null, this, this); 
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
   public Module getModule() {
      return type.getModule();
   }
   
   @Override
   public Model getModel() {
      return null;
   }
   
   @Override
   public Type getHandle() {
      return type;
   }
   
   @Override
   public Type getType(){
      return type;
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
