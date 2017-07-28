
package org.snapscript.core.define;

import org.snapscript.core.Model;
import org.snapscript.core.Module;
import org.snapscript.core.State;
import org.snapscript.core.Type;

public class SuperInstance implements Instance {

   private final Instance scope;
   private final Module module;
   private final Model model;
   private final Type type;
   private final Type real;
   
   public SuperInstance(Module module, Model model, Instance scope, Type real, Type type) {
      this.scope = scope;
      this.module = module;
      this.model = model;
      this.type = type;
      this.real = real;
   }

   @Override
   public Instance getInner() {
      return scope.getInner();
   }

   @Override
   public Instance getOuter() {
      return scope.getOuter();
   }
   
   @Override
   public Instance getSuper(){
      return scope.getSuper();
   }
   
   @Override
   public Object getObject() {
      return scope.getObject();
   }
   
   @Override
   public State getState() {
      return scope.getState();
   }
   
   public Type getHandle() {
      return type;
   }

   @Override
   public Type getType() {
      return real;
   }

   @Override
   public Module getModule() {
      return module;
   }

   @Override
   public Model getModel() {
      return model;
   }
   
   @Override
   public String toString() {
      return scope.toString();
   }
}
