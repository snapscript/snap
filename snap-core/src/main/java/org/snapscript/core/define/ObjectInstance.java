package org.snapscript.core.define;

import org.snapscript.core.Model;
import org.snapscript.core.Module;
import org.snapscript.core.Stack;
import org.snapscript.core.State;
import org.snapscript.core.Type;

public class ObjectInstance implements Instance {

   private final Instance base;
   private final Module module;
   private final Stack stack;
   private final State state;
   private final Model model;
   private final Type type;
   
   public ObjectInstance(Stack stack, Module module, Model model, Instance base, Type type, int key) {
      this.state = new InstanceState(base, stack, key);
      this.module = module;
      this.model = model;
      this.stack = stack;
      this.type = type;
      this.base = base;
      
      if(stack == null) {
         throw new IllegalStateException("Stack must not be null");
      }
   }
   
   @Override
   public Instance getInner() {
      return new CompoundInstance(stack, module, model, this, this, type);
   } 
   
   @Override
   public Stack getStack(){
      return stack;
   }
   
   @Override
   public Instance getObject() {
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
