package org.snapscript.core;

public class TypeScope implements Scope {
   
   private final State state;
   private final Type type;
   
   public TypeScope(Type type) {
      this.state = new MapState();
      this.type = type;
   }
   
   @Override
   public Scope getInner() {
      return new CompoundScope(null, this, this); 
   } 
   
   @Override
   public Scope getOuter() {
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
   public Type getType(){
      return null;
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
