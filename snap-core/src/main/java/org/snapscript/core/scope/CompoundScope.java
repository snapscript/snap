package org.snapscript.core.scope;

import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.index.ArrayTable;
import org.snapscript.core.scope.index.ScopeIndex;
import org.snapscript.core.scope.index.ScopeTable;
import org.snapscript.core.scope.index.StackIndex;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;

public class CompoundScope implements Scope {
   
   private final ScopeIndex index;
   private final ScopeTable table;
   private final ScopeState state;
   private final Scope outer;
   
   public CompoundScope(Scope inner, Scope outer) {
      this.index = new StackIndex(inner);
      this.state = new MapState(inner);
      this.table = new ArrayTable();
      this.outer = outer;
   } 
  
   @Override
   public Scope getStack() {
      throw new InternalStateException("Stack already created");
   }  
   
   @Override
   public Scope getScope() {
      return outer;
   }
   
   @Override
   public Value getThis() {
      return outer.getThis();
   }
   
   @Override
   public Type getHandle() {
      return outer.getType();
   }
   
   @Override
   public Type getType() {
      return outer.getType();
   }
  
   @Override
   public Module getModule() {
      return outer.getModule();
   } 
   
   @Override
   public ScopeIndex getIndex(){
      return index;
   }
   
   @Override
   public ScopeTable getTable(){
      return table;
   }
   
   @Override
   public ScopeState getState() {
      return state;
   }
   
   @Override
   public String toString() {
      return String.valueOf(state);
   }
}