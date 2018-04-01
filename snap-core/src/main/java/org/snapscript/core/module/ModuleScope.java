package org.snapscript.core.module;

import org.snapscript.core.scope.CompoundScope;
import org.snapscript.core.scope.MapState;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.scope.index.ArrayTable;
import org.snapscript.core.scope.index.Index;
import org.snapscript.core.scope.index.StackIndex;
import org.snapscript.core.scope.index.Table;
import org.snapscript.core.type.Type;

public class ModuleScope implements Scope {
   
   private final Module module;
   private final Index index;
   private final Table table;
   private final State state;
   
   public ModuleScope(Module module) {
      this.state = new MapState(null);
      this.index = new StackIndex();
      this.table = new ArrayTable();
      this.module = module;
   }
   
   @Override
   public Scope getStack() {
      return new CompoundScope(this, this);
   } 
   
   @Override
   public Scope getScope() {
      return this;
   }

   @Override
   public State getState() {
      return state;
   }
   
   @Override
   public Index getIndex(){
      return index;
   }
   
   @Override
   public Table getTable() {
      return table;
   }
   
   @Override
   public Type getHandle() {
      return null;
   }
   
   @Override
   public Type getType() {
      return null;
   }  

   @Override
   public Module getModule() {
      return module;
   }
   
   @Override
   public String toString() {
      return String.valueOf(state);
   }
}