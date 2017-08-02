package org.snapscript.core.closure;

import org.snapscript.core.CompoundScope;
import org.snapscript.core.Model;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Type;

public class ClosureScope implements Scope {
   
   private final State state;
   private final Scope inner;
   private final Scope outer;
   private final Model model;
   
   public ClosureScope(Model model, Scope inner, Scope outer) {
      this.state = new ClosureState(inner);
      this.inner = inner;
      this.outer = outer;
      this.model = model;
   }

   @Override
   public Scope getInner() {
      return new CompoundScope(model, this, outer);
   }
   
   @Override
   public Scope getOuter() {
      return outer;
   }
   
   @Override
   public Type getHandle() {
      return inner.getType();
   }

   @Override
   public Type getType() {
      return inner.getType();
   }

   @Override
   public Module getModule() {
      return inner.getModule();
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