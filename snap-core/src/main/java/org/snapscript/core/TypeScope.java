package org.snapscript.core;

import org.snapscript.core.State;

@Bug("maybe this shoould also contain the module scope, i.e grab the constants from the surrounding module!!")
public class TypeScope implements Scope {
   
   private final Stack stack;
   private final State state;
   private final Type type;
   
   public TypeScope(Type type, Stack stack) {
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
   public Stack getStack(){
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
