
package org.snapscript.core.define;

import org.snapscript.core.Model;
import org.snapscript.core.Module;
import org.snapscript.core.State;
import org.snapscript.core.Type;

public class ObjectInstance implements Instance {

   private final Instance base;
   private final Module module;
   private final State state;
   private final Model model;
   private final Type type;
   
   public ObjectInstance(Module module, Model model, Instance base, Type type) {
      this.state = new InstanceState(base);
      this.module = module;
      this.model = model;
      this.type = type;
      this.base = base;
   }
   
   @Override
   public Instance getInner() {
      return new CompoundInstance(module, model, this, this, type);
   } 
   
   @Override
   public Instance getOuter() {
      return this; 
   } 
   
   @Override
   public Instance getSuper(){
      return base;
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
      return type.toString();
   }
}
