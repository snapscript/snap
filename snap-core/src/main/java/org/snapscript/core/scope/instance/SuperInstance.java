package org.snapscript.core.scope.instance;

import org.snapscript.core.module.Module;
import org.snapscript.core.platform.Bridge;
import org.snapscript.core.scope.ScopeState;
import org.snapscript.core.scope.index.ScopeIndex;
import org.snapscript.core.scope.index.ScopeTable;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;

public class SuperInstance implements Instance {

   private final Instance scope;
   private final Module module;
   private final Type type;
   private final Type real;
   
   public SuperInstance(Module module, Instance scope, Type real, Type type) {
      this.scope = scope;
      this.module = module;
      this.type = type;
      this.real = real;
   }

   @Override
   public Instance getStack() {
      return scope.getStack();
   }

   @Override
   public Instance getScope() {
      return scope.getScope();
   }
   
   @Override
   public Instance getSuper(){
      return scope.getSuper();
   }
   
   @Override
   public Bridge getBridge() {
      return scope.getBridge();
   }
   
   @Override
   public Value getThis() {
      return scope.getThis();
   }
   
   @Override
   public Object getProxy() {
      return scope.getProxy();
   }
   
   @Override
   public ScopeState getState() {
      return scope.getState();
   }
   
   @Override
   public ScopeIndex getIndex(){
      return scope.getIndex();
   }
   
   @Override
   public ScopeTable getTable(){
      return scope.getTable();
   }
   
   @Override
   public Type getHandle() {
      return type;
   }

   @Override
   public Type getType() {
      return real;
   }

   @Override
   public Module getModule() {
      return module;
   }
   
   @Override
   public String toString() {
      return scope.toString();
   }
}