package org.snapscript.tree.define;

import org.snapscript.core.Context;
import org.snapscript.core.function.bind.FunctionBinder;
import org.snapscript.core.function.bind.FunctionMatcher;
import org.snapscript.core.function.dispatch.FunctionDispatcher;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;
import org.snapscript.tree.NameReference;

public class SuperFunctionMatcher {

   private FunctionDispatcher dispatcher;
   private NameReference reference;
   private Type type;
   
   public SuperFunctionMatcher(NameReference reference, Type type) {
      this.reference = reference;
      this.type = type;
   }

   public FunctionDispatcher match(Scope scope, Value left) throws Exception {
      if(dispatcher == null) {
         dispatcher = resolve(scope, left);
      }
      return dispatcher;
   }
   
   private FunctionDispatcher resolve(Scope scope, Value left) throws Exception {
      Class base = type.getType();
      
      if(base == null) {
         Module module = scope.getModule();
         Context context = module.getContext();
         FunctionBinder binder = context.getBinder();
         String name = reference.getName(scope);         
         FunctionMatcher matcher = binder.bind(name);
         
         return matcher.match(scope, left);
      }
      return new SuperDispatcher(type);
   }
   
}