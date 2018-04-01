package org.snapscript.core.function.dispatch;

import java.util.Map;

import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.Value;
import org.snapscript.core.type.Category;
import org.snapscript.core.type.Type;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.convert.proxy.Delegate;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.search.FunctionSearcher;

public class FunctionDispatcherBuilder {

   private final FunctionSearcher binder;
   private final ErrorHandler handler;
   private final String name;
   
   public FunctionDispatcherBuilder(FunctionSearcher binder, ErrorHandler handler, String name) {
      this.handler = handler;
      this.binder = binder;
      this.name = name;
   }
   
   public FunctionDispatcher create(Scope scope, Class type) throws Exception  {
      if(Module.class.isAssignableFrom(type)) {
         return new ModuleDispatcher(binder, handler, name);
      }  
      if(Type.class.isAssignableFrom(type)) {
         return new TypeStaticDispatcher(binder, handler, name);
      }  
      if(Map.class.isAssignableFrom(type)) {
         return new MapDispatcher(binder, handler, name);
      }
      if(Function.class.isAssignableFrom(type)) {
         return new ClosureDispatcher(binder, handler, name);
      }
      if(Delegate.class.isAssignableFrom(type)) { 
         return new DelegateDispatcher(binder, handler, name);
      }     
      if(Value.class.isAssignableFrom(type)) {
         return new ValueDispatcher(binder, name);
      }
      if(type.isArray()) {
         return new ArrayDispatcher(binder, handler, name);
      }
      return new TypeInstanceDispatcher(binder, handler, name);     
   }
   
   public FunctionDispatcher create(Scope scope, Constraint left) throws Exception {
      Type type = left.getType(scope);
      Category category = type.getCategory();
      Class real = type.getType();
      
      if(left.isModule()) {
         return new ModuleDispatcher(binder, handler, name);
      }
      if(left.isStatic()) {
         return new TypeStaticDispatcher(binder, handler, name);
      }
      if(category.isFunction()) {
         return new ClosureDispatcher(binder, handler, name);
      }
      if(category.isProxy()) { 
         return new DelegateDispatcher(binder, handler, name);
      } 
      if(category.isArray()) {
         return new ArrayDispatcher(binder, handler, name);
      }
      if(real != null) {
         if(Map.class.isAssignableFrom(real)) {
            return new MapDispatcher(binder, handler, name);
         }
      }
      return new TypeInstanceDispatcher(binder, handler, name);      
   }
}
