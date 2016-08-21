package org.snapscript.core.define;

import org.snapscript.core.MapState;
import org.snapscript.core.Model;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Type;

public class PrimitiveInstance implements Instance {
   
   private final Module module;
   private final State state;
   private final Model model;
   private final Type type;
   
   public PrimitiveInstance(Module module, Model model, Scope scope, Type type) {
      this.state = new MapState(model, scope);
      this.module = module;
      this.model = model;
      this.type = type;
   }
   
   @Override
   public Instance getInner() {
      return new CompoundInstance(module, model, this, this, type);
   } 
   
   @Override
   public Instance getOuter() {
      return this;
   } 
   
   public Instance getSuper(){
      return null;
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
