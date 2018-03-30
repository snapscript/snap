package org.snapscript.core.dispatch;

import java.util.Map;

import org.snapscript.core.Category;
import org.snapscript.core.Constraint;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.core.convert.Delegate;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.Function;

public class CallDispatcherBuilder {

   private final FunctionBinder binder;
   private final ErrorHandler handler;
   private final String name;
   
   public CallDispatcherBuilder(FunctionBinder binder, ErrorHandler handler, String name) {
      this.handler = handler;
      this.binder = binder;
      this.name = name;
   }
   
   public CallDispatcher create(Scope scope, Class type) throws Exception  {
      if(Scope.class.isAssignableFrom(type)) {
         return new DynamicDispatcher(binder, handler, name);            
      }
      if(Module.class.isAssignableFrom(type)) {
         return new ModuleDispatcher(binder, handler, name);
      }  
      if(Type.class.isAssignableFrom(type)) {
         return new TypeDispatcher(binder, handler, name);
      }  
      if(Map.class.isAssignableFrom(type)) {
         return new MapDispatcher(binder, handler, name);
      }
      if(Function.class.isAssignableFrom(type)) {
         return new FunctionDispatcher(binder, handler, name);
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
      return new ObjectDispatcher(binder, handler, name);     
   }
   
   public CallDispatcher create(Scope scope, Constraint left) throws Exception {
      Type type = left.getType(scope);
      
      if(type != null) {
         Category category = type.getCategory();
         Class real = type.getType();
         
         if(left.isModule()) {
            return new ModuleDispatcher(binder, handler, name);
         }
         if(left.isStatic()) {
            return new TypeDispatcher(binder, handler, name);
         }
         if(category.isFunction()) {
            return new FunctionDispatcher(binder, handler, name);
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
            return new ObjectDispatcher(binder, handler, name);     
         }
      }
      return new DynamicDispatcher(binder, handler, name);  
   }
}
