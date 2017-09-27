package org.snapscript.tree.dispatch;

import java.util.Map;

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.convert.Delegate;
import org.snapscript.core.function.Function;
import org.snapscript.tree.NameReference;

public class InvocationBinder {
   
   private final Cache<Class, InvocationDispatcher> cache;
   private final InvocationDispatcher instance;
   private final InvocationDispatcher local;
   private final NameReference reference;
   
   public InvocationBinder(NameReference reference) {
      this.cache = new CopyOnWriteCache<Class, InvocationDispatcher>();
      this.instance = new InstanceDispatcher(reference);
      this.local = new LocalDispatcher(reference);
      this.reference = reference;
   }
   
   public InvocationDispatcher bind(Scope scope, Object left) {
      if(left != null) {
         Class type = left.getClass();
         InvocationDispatcher dispatcher = cache.fetch(type);
         
         if(dispatcher == null) { 
            dispatcher = create(scope, type);
            cache.cache(type, dispatcher);
         }
         return dispatcher;
      }
      Type type = scope.getType();
      
      if(type != null) {
         return instance;
      }
      return local; 
   }

   public InvocationDispatcher create(Scope scope, Class type) {
      if(Scope.class.isAssignableFrom(type)) {
         return new ScopeDispatcher(reference);            
      }
      if(Module.class.isAssignableFrom(type)) {
         return new ModuleDispatcher(reference);
      }  
      if(Type.class.isAssignableFrom(type)) {
         return new TypeDispatcher(reference);
      }  
      if(Map.class.isAssignableFrom(type)) {
         return new MapDispatcher(reference);
      }
      if(Function.class.isAssignableFrom(type)) {
         return new FunctionDispatcher(reference);
      }
      if(Delegate.class.isAssignableFrom(type)) { 
         return new DelegateDispatcher(reference);
      }     
      if(Value.class.isAssignableFrom(type)) {
         return new ValueDispatcher(reference);
      }
      if(type.isArray()) {
         return new ArrayDispatcher(reference);
      }
      return new ObjectDispatcher(reference);     
   }
}