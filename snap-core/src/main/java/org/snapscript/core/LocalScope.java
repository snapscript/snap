package org.snapscript.core;

import org.snapscript.core.ArrayTable;
import org.snapscript.core.CompoundScope;
import org.snapscript.core.Index;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.StackIndex;
import org.snapscript.core.State;
import org.snapscript.core.Type;

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