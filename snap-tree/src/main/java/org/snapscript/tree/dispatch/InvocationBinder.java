package org.snapscript.tree.dispatch;

import java.lang.reflect.Proxy;
import java.util.Map;

import org.snapscript.core.Bug;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.convert.Delegate;
import org.snapscript.core.function.Function;

public class InvocationBinder {
   
   public InvocationBinder() {
      super();
   }

   @Bug("we should never really have Proxy objects here, Proxy objects are only for invocation of native Java methods... Delegates are ok")
   public InvocationDispatcher bind(Scope scope, Object left) {
      if(left != null) {
         Class type = left.getClass();
         
         if(Scope.class.isInstance(left)) {
            return new ScopeDispatcher(scope, left);            
         }
         if(Module.class.isInstance(left)) {
            return new ModuleDispatcher(scope, left);
         }  
         if(Type.class.isInstance(left)) {
            return new TypeDispatcher(scope, left);
         }  
         if(Map.class.isInstance(left)) {
            return new MapDispatcher(scope, left);
         }
         if(Function.class.isInstance(left)) {
            return new FunctionDispatcher(scope, left);
         }
         if(Delegate.class.isInstance(left)) { // this is a function 
            return new DelegateDispatcher(scope, left);
         }
         if(Proxy.class.isInstance(left)) { 
            return new DelegateDispatcher(scope, left);
         }         
         if(type.isArray()) {
            return new ArrayDispatcher(scope, left);
         }
         return new ObjectDispatcher(scope, left);
      }
      Type type = scope.getType();
      
      if(type != null) {
         return new ScopeDispatcher(scope, scope);
      }
      return new LocalDispatcher(scope);      
   }
}