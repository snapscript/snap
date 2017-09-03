package org.snapscript.core;

import java.util.ArrayList;
import java.util.List;

public class CompoundScope implements Scope {
   
   private final State state;
   private final Scope outer;
   private final Model model;
   
   public CompoundScope(Model model, Scope inner, Scope outer) {
      this(model, inner, outer, null);
   }
   
   public CompoundScope(Model model, Scope inner, Scope outer, List<Local> stack) {
      this.state = new MapState(model, inner, stack);  
      this.outer = outer;
      this.model = model;
   } 
  
   @Override
   public Scope getInner() {
      throw new IllegalStateException("stack inner");
      //return new StateScope(model, this, outer, state.getStack());
   }  
   
   @Override
   public Scope getOuter() {
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
      private final List<Local> stack;
      
      public StateScope(Model model, Scope inner, Scope outer) {
         this(model, inner, outer, null);
      }
      
      public StateScope(Model model, Scope inner, Scope outer, List<Local> stack) {
         this.stack = stack == null ?new ArrayList<Local>() : stack;
         this.state = new MapState(null, inner, this.stack); // ignore model
         this.outer = outer;
         this.model = model;
      }

      @Override
      public Scope getInner() {
         return new StateScope(model, this, outer, stack);
      }
      
      @Override
      public Scope getOuter() {
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