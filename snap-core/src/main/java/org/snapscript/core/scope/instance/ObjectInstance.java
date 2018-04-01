package org.snapscript.core.scope.instance;

import org.snapscript.core.convert.proxy.ScopeProxy;
import org.snapscript.core.module.Module;
import org.snapscript.core.platform.Bridge;
import org.snapscript.core.scope.State;
import org.snapscript.core.scope.index.ArrayTable;
import org.snapscript.core.scope.index.Index;
import org.snapscript.core.scope.index.StackIndex;
import org.snapscript.core.scope.index.Table;
import org.snapscript.core.type.Type;

public class ObjectInstance implements Instance {

   private final ScopeProxy proxy;
   private final Instance base;
   private final Bridge object;
   private final Module module;
   private final Table table;
   private final Index index;
   private final State state;
   private final Type type;
   
   public ObjectInstance(Module module, Instance base, Bridge object, Type type) {
      this.state = new InstanceState(base);
      this.proxy = new ScopeProxy(this);
      this.table = new ArrayTable();
      this.index = new StackIndex();
      this.object = object;
      this.module = module;
      this.type = type;
      this.base = base;
   }
   
   @Override
   public Instance getStack() {
      return new CompoundInstance(module, this, this, type);
   } 
   
   @Override
   public Object getProxy() {
      return proxy.getProxy();
   }
   
   @Override
   public Instance getScope() {
      return this; 
   } 
   
   @Override
   public Instance getSuper(){
      return base;
   }
   
   @Override
   public Bridge getBridge() {
      return object;
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