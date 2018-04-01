package org.snapscript.core.scope.instance;

import org.snapscript.core.module.Module;
import org.snapscript.core.platform.Bridge;
import org.snapscript.core.scope.State;
import org.snapscript.core.scope.index.Index;
import org.snapscript.core.scope.index.Table;
import org.snapscript.core.type.Type;

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
   public Object getProxy() {
      return scope.getProxy();
   }
   
   @Override
   public State getState() {
      return scope.getState();
   }
   
   @Override
   public Index getIndex(){
      return scope.getIndex();
   }
   
   @Override
   public Table getTable(){
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