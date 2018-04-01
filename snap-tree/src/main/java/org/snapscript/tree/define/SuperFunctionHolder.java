package org.snapscript.tree.define;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.function.dispatch.FunctionDispatcher;
import org.snapscript.tree.NameReference;
import org.snapscript.tree.function.FunctionHolder;

public class SuperFunctionHolder {

   private final FunctionDispatcher dispatcher;
   private final FunctionHolder holder;
   private final Type type;
   
   public SuperFunctionHolder(NameReference reference, Type type) {
      this.dispatcher = new SuperDispatcher(type);
      this.holder = new FunctionHolder(reference);
      this.type = type;
   }
   
   public FunctionDispatcher get(Scope scope, Type left) throws Exception {
      Class base = type.getType();
      
      if(base == null) {
         return holder.get(scope, left);
      }
      return dispatcher;
   }
   
   public FunctionDispatcher get(Scope scope, Object left) throws Exception {
      Class base = type.getType();
      
      if(base == null) {
         return holder.get(scope, left);
      }
      return dispatcher;
   }
}