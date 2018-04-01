package org.snapscript.tree.variable;

import java.util.Collection;
import java.util.Map;

import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.instance.Instance;
import org.snapscript.core.type.Category;
import org.snapscript.core.type.Type;
import org.snapscript.core.constraint.Constraint;

public class VariablePointerBuilder {

   private final VariableFinder finder;
   private final String name;
   
   public VariablePointerBuilder(String name) {
      this.finder = new VariableFinder();
      this.name = name;
   }

   public VariablePointer create(Scope scope) throws Exception {
      if(Instance.class.isInstance(scope)) {
         return new InstancePointer(finder, name);
      }
      return new LocalPointer(finder, name);
   }
   
   public VariablePointer create(Scope scope, Object left) throws Exception {
      if(left != null) {
         Class type = left.getClass();
         
         if(Module.class.isInstance(left)) {
            return new ModulePointer(finder, name);
         }
         if(Map.class.isInstance(left)) {
            return new MapPointer(finder, name);
         }         
         if(Scope.class.isInstance(left)) {
            return new ScopePointer(finder, name);
         }
         if(Type.class.isInstance(left)) {
            return new TypePointer(finder, name);
         }
         if(Collection.class.isInstance(left)) {
            return new CollectionPointer(finder, name);
         }
         if(type.isArray()) {
            return new ArrayPointer(finder, name);
         }
         return new ObjectPointer(finder, name);
      }
      return create(scope);
   }
   
   public VariablePointer create(Scope scope, Constraint left) throws Exception {
      Type type = left.getType(scope);
      
      if(type != null) {
         Category category = type.getCategory();
         Class real = type.getType();
         
         if(left.isModule()) {
            return new ModulePointer(finder, name);
         }
         if(left.isStatic()) {
            return new TypePointer(finder, name);
         }
         if(category.isArray()) {
            return new ArrayPointer(finder, name);
         }
         if(real != null) {
            if(Map.class.isAssignableFrom(real)) {
               return new MapPointer(finder, name);
            }         
            if(Collection.class.isAssignableFrom(real)) {
               return new CollectionPointer(finder, name);
            }
            return new ObjectPointer(finder, name);
         }
      }
      return new ScopePointer(finder, name);
   }
}