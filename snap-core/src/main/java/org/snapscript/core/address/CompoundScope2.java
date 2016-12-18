package org.snapscript.core.address;

import org.snapscript.core.MapState;
import org.snapscript.core.Model;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Type;

public class CompoundScope2 implements Scope2 {

   private final State stack;
   private final State state;
   private final Scope outer;
   private final Model model;
   
   public CompoundScope2(State stack, Model model, Scope inner, Scope outer) {
      this.state = new MapState(model, inner);  
      this.stack = stack;
      this.outer = outer;
      this.model = model;
   } 
  
   @Override
   public State getStack() {
      return stack;
   }  
   
   @Override
   public State getState() {
      return null;
   }
   
   @Override
   public Scope getObject() {
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
   public String toString() {
      return String.valueOf(state);
   }
   
   private static class StateScope implements Scope {
      
      private final State stack;
      private final State state;
      private final Scope outer;
      private final Model model;
      
      public StateScope(State stack, Model model, Scope inner, Scope outer) {
         this.state = new MapState(null, inner); // ignore model
         this.stack = stack;
         this.outer = outer;
         this.model = model;
         
         if(stack == null) {
            throw new IllegalStateException("Stack must not be null");
         }
      }

      @Override
      public Scope getInner() {
         return new StateScope(stack, model, this, outer);
      }
      
      @Override
      public State getStack(){
         return stack;
      }
      
      @Override
      public Scope getObject() {
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
