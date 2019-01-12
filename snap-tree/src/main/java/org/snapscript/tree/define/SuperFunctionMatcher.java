package org.snapscript.tree.define;

import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;

import org.snapscript.core.Context;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.bind.FunctionBinder;
import org.snapscript.core.function.bind.FunctionMatcher;
import org.snapscript.core.function.dispatch.FunctionDispatcher;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;

public class SuperFunctionMatcher {

   private FunctionDispatcher dispatcher;
   private Type type;
   
   public SuperFunctionMatcher(Type type) {
      this.type = type;
   }
   
   public FunctionDispatcher match(Scope scope, Constraint left) throws Exception {
      return resolve(scope, left);
   }

   public FunctionDispatcher match(Scope scope, Value left) throws Exception {
      if(dispatcher == null) {
         dispatcher = resolve(scope, left);
      }
      return dispatcher;
   }
   
   private FunctionDispatcher resolve(Scope scope, Constraint left) throws Exception {
      Class base = type.getType();
      
      if(base == null) {
         Module module = scope.getModule();
         Context context = module.getContext();
         FunctionBinder binder = context.getBinder();     
         FunctionMatcher matcher = binder.bind(TYPE_CONSTRUCTOR);
         
         return matcher.match(scope, left);
      }
      return new SuperDispatcher(type);
   }
   
   private FunctionDispatcher resolve(Scope scope, Value left) throws Exception {
      Class base = type.getType();
      
      if(base == null) {
         Module module = scope.getModule();
         Context context = module.getContext();
         FunctionBinder binder = context.getBinder();
         FunctionMatcher matcher = binder.bind(TYPE_CONSTRUCTOR);
         
         return matcher.match(scope, left);
      }
      return new SuperDispatcher(type);
   }
   
}