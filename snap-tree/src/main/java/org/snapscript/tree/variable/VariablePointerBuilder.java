package org.snapscript.tree.variable;

import java.util.Collection;
import java.util.Map;

import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.define.Instance;

public class VariablePointerBuilder {

   private final ConstantResolver resolver;
   private final String name;
   
   public VariablePointerBuilder(String name) {
      this.resolver = new ConstantResolver();
      this.name = name;
   }

   public VariablePointer create(Scope scope) throws Exception {
      if(Instance.class.isInstance(scope)) {
         return new InstancePointer(resolver, name);
      }
      return new LocalPointer(resolver, name);
   }
   
   public VariablePointer create(Scope scope, Object left) throws Exception {
      if(left != null) {
         Class type = left.getClass();
         
         if(Module.class.isInstance(left)) {
            return new ModulePointer(resolver, name);
         }
         if(Map.class.isInstance(left)) {
            return new MapPointer(resolver, name);
         }         
         if(Scope.class.isInstance(left)) {
            return new ScopePointer(name);
         }
         if(Type.class.isInstance(left)) {
            return new TypePointer(resolver, name);
         }
         if(Collection.class.isInstance(left)) {
            return new CollectionPointer(resolver, name);
         }
         if(type.isArray()) {
            return new ArrayPointer(resolver, name);
         }
         return new ObjectPointer(resolver, name);
      }
      return create(scope);
   }
   
   public VariablePointer create(Scope scope, Type left) throws Exception {
      if(left != null) {
         Class type = left.getType();
         
         if(Module.class.isInstance(left)) {
            return new ModulePointer(resolver, name);
         }
         if(Map.class.isInstance(left)) {
            return new MapPointer(resolver, name);
         }         
         if(Scope.class.isInstance(left)) {
            return new ScopePointer(name);
         }
         if(Type.class.isInstance(left)) {
            return new TypePointer(resolver, name);
         }
         if(Collection.class.isInstance(left)) {
            return new CollectionPointer(resolver, name);
         }
         if(type.isArray()) {
            return new ArrayPointer(resolver, name);
         }
         return new ObjectPointer(resolver, name);
      }
      return create(scope);
   }
}