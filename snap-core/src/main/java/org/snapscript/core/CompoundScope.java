package org.snapscript.core;

public class CompoundScope implements Scope {
   
   private final State state;
   private final Scope outer;
   private final Model model;
   
   public CompoundScope(Model model, Scope inner, Scope outer) {
      this.state = new MapState(model, inner);  
      this.outer = outer;
      this.model = model;
   } 
  
   @Override
   public Scope getInner() {
      return new StateScope(model, this, outer);
   }  
   
   @Override
   public Scope getOuter() {
      return outer;
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
   
   private static class StateScope implements Scope {
      
      private final State state;
      private final Scope outer;
      private final Model model;
      
      public StateScope(Model model, Scope inner, Scope outer) {
         this.state = new MapState(null, inner); // ignore model
         this.outer = outer;
         this.model = model;
      }

      @Override
      public Scope getInner() {
         return new StateScope(model, this, outer);
      }
      
      @Override
      public Scope getOuter() {
         return outer;
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
}
