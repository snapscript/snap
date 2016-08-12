package org.snapscript.tree.variable;

import java.util.Collection;
import java.util.Map;

import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.define.Instance;

public class VariableBinder {
   
   public VariableBinder() {
      super();
   }
   
   public ValueResolver bind(Scope scope, Object left, String name) {
      if(left != null) {
         Class type = left.getClass();
         
         if(Module.class.isInstance(left)) {
            return new ModuleResolver(name);
         }
         if(Map.class.isInstance(left)) {
            return new MapResolver(name);
         }         
         if(Scope.class.isInstance(left)) {
            return new ScopeResolver(name);
         }
         if(Type.class.isInstance(left)) {
            return new TypeResolver(name);
         }
         if(Collection.class.isInstance(left)) {
            return new CollectionResolver(name);
         }
         if(type.isArray()) {
            return new ArrayResolver(name);
         }
         return new ObjectResolver(name);
      }
      if(Instance.class.isInstance(scope)) {
         return new InstanceResolver(name);
      }
      return new LocalResolver(name);
   }
}
