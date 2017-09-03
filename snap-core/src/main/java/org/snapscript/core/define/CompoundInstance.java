package org.snapscript.core.define;

import java.util.List;

import org.snapscript.core.Local;
import org.snapscript.core.MapState;
import org.snapscript.core.Model;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Type;
import org.snapscript.core.platform.Bridge;

public class CompoundInstance implements Instance {
   
   private final Instance instance;
   private final Module module;
   private final State state;
   private final Model model;
   private final Scope outer;
   private final Type type;
   
   public CompoundInstance(Module module, Model model, Instance instance, Scope outer, Type type) {
      this(module, model, instance, outer, type, null);
   }
   
   public CompoundInstance(Module module, Model model, Instance instance, Scope outer, Type type, List<Local> stack) {
      this.state = new MapState(model, outer, stack);
      this.instance = instance;
      this.module = module;
      this.outer = outer;
      this.model = model;
      this.type = type;
   }
   
   @Override
   public Instance getInner() {
      throw new IllegalStateException("Unable to get inner");
      //return new CompoundInstance(module, model, instance, this, type, state.getStack());
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
   public Bridge getBridge() {
      return instance.getBridge();
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