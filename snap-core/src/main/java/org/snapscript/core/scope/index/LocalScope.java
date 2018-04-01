package org.snapscript.core.scope.index;

import org.snapscript.core.module.Module;
import org.snapscript.core.scope.CompoundScope;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.type.Type;

public class LocalScope implements Scope {
   
   private final Index index;
   private final Table table;
   private final State state;
   private final Scope inner;
   private final Scope outer;
   
   public LocalScope(Scope inner, Scope outer) {
      this.state = new LocalState(inner);
      this.table = new ArrayTable();
      this.index = new StackIndex();
      this.inner = inner;
      this.outer = outer;
   }

   @Override
   public Scope getStack() {
      return new CompoundScope(this, outer);
   }
   
   @Override
   public Scope getScope() {
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
   public Table getTable(){
      return table;
   }
   
   @Override
   public Index getIndex(){
      return index;
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