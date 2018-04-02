package org.snapscript.core.scope.instance;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.module.Module;
import org.snapscript.core.platform.Bridge;
import org.snapscript.core.scope.MapState;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.scope.index.ArrayTable;
import org.snapscript.core.scope.index.Index;
import org.snapscript.core.scope.index.StackIndex;
import org.snapscript.core.scope.index.Table;
import org.snapscript.core.type.Type;

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
      throw new InternalStateException("Stack already created");
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
   public Object getProxy() {
      return instance.getProxy();
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