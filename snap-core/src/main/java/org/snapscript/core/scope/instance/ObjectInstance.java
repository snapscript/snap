package org.snapscript.core.scope.instance;

import org.snapscript.core.convert.proxy.ScopeProxy;
import org.snapscript.core.module.Module;
import org.snapscript.core.platform.Bridge;
import org.snapscript.core.scope.ScopeState;
import org.snapscript.core.scope.index.ArrayTable;
import org.snapscript.core.scope.index.ScopeIndex;
import org.snapscript.core.scope.index.ScopeTable;
import org.snapscript.core.scope.index.StackIndex;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;

public class ObjectInstance implements Instance {

   private final ScopeTable table;
   private final ScopeIndex index;
   private final ScopeState state;
   private final ScopeProxy proxy;
   private final Instance base;
   private final Bridge object;
   private final Module module;
   private final Value self;
   private final Type type;
   
   public ObjectInstance(Module module, Instance base, Bridge object, Value self, Type type) {
      this.state = new InstanceState(base);
      this.index = new StackIndex(base);
      this.proxy = new ScopeProxy(this);
      this.table = new ArrayTable();
      this.object = object;
      this.module = module;
      this.type = type;
      this.base = base;
      this.self = self;
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
   public Value getThis() {
      return self;
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