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

public class PrimitiveInstance implements Instance {
   
   private final Module module;
   private final Index index;
   private final Table table;
   private final State state;
   private final Type type;
   
   public PrimitiveInstance(Module module, Scope scope, Type type) {
      this.state = new MapState(scope);
      this.table = new ArrayTable();
      this.index = new StackIndex();
      this.module = module;
      this.type = type;
   }
   
   @Override
   public Instance getStack() {
      return new CompoundInstance(module, this, this, type);
   } 
   
   @Override
   public Instance getScope() {
      return this;
   } 
   
   @Override
   public Instance getSuper(){
      return null;
   }
   
   @Override
   public Bridge getBridge(){
      return null;
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
      return type.toString();
   }
}