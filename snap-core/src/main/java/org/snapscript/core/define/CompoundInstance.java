package org.snapscript.core.define;

import org.snapscript.core.MapState;
import org.snapscript.core.Model;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Type;

public class CompoundInstance implements Instance {
   
   private final Instance instance;
   private final Module module;
   private final State stack;
   private final State state;
   private final Model model;
   private final Scope outer;
   private final Type type;
   
   public CompoundInstance(State stack, Module module, Model model, Instance instance, Scope outer, Type type) {
      this.state = new MapState(model, outer);
      this.instance = instance;
      this.module = module;
      this.outer = outer;
      this.model = model;
      this.stack = stack;
      this.type = type;
      
      if(stack == null) {
         throw new IllegalStateException("Stack must not be null");
      }
   }
   
   @Override
   public Instance getInner() {
      return new CompoundInstance(stack, module, model, instance, this, type);
   }
   
   @Override
   public State getStack(){
      return stack;
   }
   
   @Override
   public Instance getObject() {
      return instance;
   } 
   
   @Override
   public Instance getSuper() {
      return instance.getSuper();
   }
  
   @Override
   public Module getModule() {
      return module;
   }
   
   @Override
   public Type getHandle(){
      return type;
   }
   
   @Override
   public Type getType(){
      return type;
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
   public String toString(){
      return outer.toString();
   }
}
