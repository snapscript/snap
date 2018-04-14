package org.snapscript.core.function.dispatch;

import java.util.Map;

import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Category;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.convert.proxy.Delegate;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.resolve.FunctionResolver;

public class FunctionDispatcherBuilder {

   private final FunctionResolver resolver;
   private final ErrorHandler handler;
   private final String name;
   
   public FunctionDispatcherBuilder(FunctionResolver resolver, ErrorHandler handler, String name) {
      this.handler = handler;
      this.resolver = resolver;
      this.name = name;
   }
   
   public FunctionDispatcher create(Scope scope, Class type) throws Exception  {
      if(Module.class.isAssignableFrom(type)) {
         return new ModuleDispatcher(resolver, handler, name);
      }  
      if(Type.class.isAssignableFrom(type)) {
         return new TypeStaticDispatcher(resolver, handler, name);
      }  
      if(Map.class.isAssignableFrom(type)) {
         return new MapDispatcher(resolver, handler, name);
      }
      if(Function.class.isAssignableFrom(type)) {
         return new ClosureDispatcher(resolver, handler, name);
      }
      if(Delegate.class.isAssignableFrom(type)) { 
         return new DelegateDispatcher(resolver, handler, name);
      }     
      if(Value.class.isAssignableFrom(type)) {
         return new ValueDispatcher(resolver, handler, name);
      }
      if(type.isArray()) {
         return new ArrayDispatcher(resolver, handler, name);
      }
      return new TypeInstanceDispatcher(resolver, handler, name);     
   }
   
   public FunctionDispatcher create(Scope scope, Constraint left) throws Exception {
      Type type = left.getType(scope);
      Category category = type.getCategory();
      Class real = type.getType();
      
      if(left.isModule()) {
         return new ModuleDispatcher(resolver, handler, name);
      }
      if(left.isClass()) {
         return new TypeStaticDispatcher(resolver, handler, name);
      }
      if(category.isFunction()) {
         return new ClosureDispatcher(resolver, handler, name);
      }
      if(category.isProxy()) { 
         return new DelegateDispatcher(resolver, handler, name);
      } 
      if(category.isArray()) {
         return new ArrayDispatcher(resolver, handler, name);
      }
      if(real != null) {
         if(Map.class.isAssignableFrom(real)) {
            return new MapDispatcher(resolver, handler, name);
         }
      }
      return new TypeInstanceDispatcher(resolver, handler, name);      
   }
}
