package org.snapscript.core.define;

import org.snapscript.core.Model;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Type;

public class StaticInstance implements Instance {
   
   private final Instance base;
   private final Module module;
   private final State state;
   private final Model model;
   private final Type type;
   
   public StaticInstance(Module module, Model model, Scope inner, Instance base, Type type) {
      this.state = new StaticState(inner, base);
      this.module = module;
      this.base = base;
      this.model = model;
      this.type = type;
   }
   
   @Override
   public Instance getInner() {
      return new StaticInstance(module, model, this, base, type);
   } 
   
   @Override
   public Instance getOuter() {
      return base; 
   } 
   
   @Override
   public Instance getInstance() {
      return base.getInstance(); 
   } 
   
   @Override
   public void setInstance(Instance instance) {
      base.setInstance(instance);
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
      return base.toString();
   }
}
