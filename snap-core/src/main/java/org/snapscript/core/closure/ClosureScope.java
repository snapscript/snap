package org.snapscript.core.closure;

import org.snapscript.core.CompoundScope;
import org.snapscript.core.Model;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Stack;
import org.snapscript.core.State;
import org.snapscript.core.Type;

public class ClosureScope implements Scope {
   
   private final Stack stack;
   private final State state;
   private final Scope scope;
   private final Model model;
   
   public ClosureScope(Stack stack, Model model, Scope scope) {
      this.state = new ClosureState(scope);
      this.stack = stack;
      this.scope = scope;
      this.model = model;
      
      if(stack == null) {
         throw new IllegalStateException("Stack must not be null");
      }
   }

   @Override
   public Scope getInner() {
      return new CompoundScope(stack, model, this, scope);
   }
   
   @Override
   public Stack getStack(){
      return stack;
   }
   
   @Override
   public Scope getObject() {
      return scope;
   }
   
   @Override
   public Type getHandle() {
      return scope.getType();
   }

   @Override
   public Type getType() {
      return scope.getType();
   }

   @Override
   public Module getModule() {
      return scope.getModule();
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