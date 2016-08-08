package org.snapscript.tree.dispatch;

import java.util.Map;

import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.ValueTypeExtractor;
import org.snapscript.core.function.Function;

public class InvocationBinder {
   
   private final ValueTypeExtractor extractor;
   
   public InvocationBinder() {
      this.extractor = new ValueTypeExtractor();
   }

   public InvocationDispatcher bind(Scope scope, Object left) {
      if(left != null) {
         Class type = left.getClass();
         
         if(Scope.class.isInstance(left)) {
            return new ScopeDispatcher(extractor, scope, left);            
         }
         if(Module.class.isInstance(left)) {
            return new ModuleDispatcher(extractor, scope, left);
         }  
         if(Type.class.isInstance(left)) {
            return new TypeDispatcher(extractor, scope, left);
         }  
         if(Map.class.isInstance(left)) {
            return new MapDispatcher(extractor, scope, left);
         }
         if(Function.class.isInstance(left)) {
            return new FunctionDispatcher(extractor, scope, left);
         }
         if(type.isArray()) {
            return new ArrayDispatcher(extractor, scope, left);
         }
         return new ObjectDispatcher(extractor, scope, left);
      }
      Type type = scope.getType();
      
      if(type != null) {
         return new ScopeDispatcher(extractor, scope, scope);
      }
      return new LocalDispatcher(scope);      
   }
}