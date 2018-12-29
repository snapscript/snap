package org.snapscript.core.scope.instance;

import org.snapscript.core.module.Module;
import org.snapscript.core.platform.Bridge;
import org.snapscript.core.scope.MapState;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.ScopeState;
import org.snapscript.core.scope.index.ArrayTable;
import org.snapscript.core.scope.index.ScopeIndex;
import org.snapscript.core.scope.index.ScopeTable;
import org.snapscript.core.scope.index.StackIndex;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Reference;
import org.snapscript.core.variable.Value;

public class PrimitiveInstance implements Instance {   
   
   private final ScopeIndex index;
   private final ScopeTable table;
   private final ScopeState state;
   private final Module module;
   private final Value self;
   private final Type type;
   
   public PrimitiveInstance(Module module, Scope scope, Type type) {
      this.index = new StackIndex(scope);
      this.state = new MapState(scope);
      this.self = new Reference(this);
      this.table = new ArrayTable();
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
   public Value getThis(){
      return self;
   }
   
   @Override
   public Object getProxy() {
      return null;
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
   public String toString(){
      return type.toString();
   }
}