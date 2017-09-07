package org.snapscript.core.define;

import org.snapscript.core.ArrayTable;
import org.snapscript.core.Index;
import org.snapscript.core.StackIndex;
import org.snapscript.core.MapState;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Table;
import org.snapscript.core.Type;
import org.snapscript.core.platform.Bridge;

public class CompoundInstance implements Instance {
   
   private final Instance instance;
   private final Module module;
   private final Index index;
   private final State state;
   private final Table table;
   private final Scope outer;
   private final Type type;
   
   public CompoundInstance(Module module, Instance instance, Scope outer, Type type) {
      this.state = new MapState(outer);
      this.table = new ArrayTable();
      this.index = new StackIndex();
      this.instance = instance;
      this.module = module;
      this.outer = outer;
      this.type = type;
   }
   
   @Override
   public Instance getStack() {
      throw new IllegalStateException("Unable to get inner");
   } 
   
   @Override
   public Instance getScope() {
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
   public Index getIndex(){
      return index;
   }
  
   @Override
   public Table getTable(){
      return table;
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
   public State getState() {
      return state;
   }

   @Override
   public String toString(){
      return outer.toString();
   }
}