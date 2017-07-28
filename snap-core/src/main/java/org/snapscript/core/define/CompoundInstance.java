
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
   private final State state;
   private final Model model;
   private final Scope outer;
   private final Type type;
   
   public CompoundInstance(Module module, Model model, Instance instance, Scope outer, Type type) {
      this.state = new MapState(model, outer);
      this.instance = instance;
      this.module = module;
      this.outer = outer;
      this.model = model;
      this.type = type;
   }
   
   @Override
   public Instance getInner() {
      return new CompoundInstance(module, model, instance, this, type);
   } 
   
   @Override
   public Instance getOuter() {
      return instance;
   } 
   
   @Override
   public Instance getSuper() {
      return instance.getSuper();
   }
   
   @Override
   public Object getObject() {
      return instance.getObject();
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
