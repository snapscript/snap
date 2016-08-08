package org.snapscript.core.define;

import org.snapscript.core.MapState;
import org.snapscript.core.Model;
import org.snapscript.core.Module;
import org.snapscript.core.State;
import org.snapscript.core.Type;

public class CompoundInstance implements Instance {
   
   private final Instance outer;
   private final Module module;
   private final State state;
   private final Model model;
   private final Type type;
   
   public CompoundInstance(Module module, Model model, Instance outer, Type type) {
      this.state = new MapState(model, outer);
      this.module = module;
      this.outer = outer;
      this.model = model;
      this.type = type;
   }
   
   @Override
   public Instance getInner() {
      return new CompoundInstance(module, model, this, type);
   } 
   
   @Override
   public Instance getOuter() {
      return outer.getOuter(); 
   } 
   
   @Override
   public Instance getInstance() {
      return outer.getInstance();
   } 
   
   @Override
   public void setInstance(Instance instance) {
      outer.setInstance(instance);
   }
  
   @Override
   public Module getModule() {
      return module;
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
