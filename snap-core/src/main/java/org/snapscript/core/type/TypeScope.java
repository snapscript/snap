package org.snapscript.core.type;

import org.snapscript.core.module.Module;
import org.snapscript.core.scope.CompoundScope;
import org.snapscript.core.scope.MapState;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.scope.index.ArrayTable;
import org.snapscript.core.scope.index.Index;
import org.snapscript.core.scope.index.StackIndex;
import org.snapscript.core.scope.index.Table;

public class TypeScope implements Scope {
   
   private final Index index;
   private final Table table;
   private final State state;
   private final Type type;
   
   public TypeScope(Type type) {
      this.table = new ArrayTable();
      this.state = new MapState();
      this.index = new StackIndex();
      this.type = type;
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
   public Module getModule() {
      return type.getModule();
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
      return type;
   }
   
   @Override
   public Type getType(){
      return type;
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