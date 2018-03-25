package org.snapscript.tree.variable;

import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.Bug;
import org.snapscript.core.Constraint;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.property.Property;
import org.snapscript.core.property.PropertyValue;

public class ModulePointer implements VariablePointer<Module> {
   
   private final AtomicReference<Property> reference;
   private final ConstantResolver resolver;
   private final String name;
   
   public ModulePointer(ConstantResolver resolver, String name) {
      this.reference = new AtomicReference<Property>();
      this.resolver = resolver;
      this.name = name;
   }

   @Bug("messy")
   @Override
   public Constraint check(Scope scope, Constraint left) {
      Property property = reference.get();
      
      if(property == null) {
         Type ty = left.getType(scope);
         Module module = ty.getModule();
         State state = scope.getState();
         Value value = state.get(name);
         
         if(value == null) {
            Type type = module.getType(name);
            
            if(type != null) {
               return Constraint.getInstance(type);
            }
            Property match = resolver.matchFromModule(scope, module, name);
            
            if(match != null) {
               reference.set(match);
               return match.getConstraint();
            }
         }
         return Constraint.getInstance(value.getConstraint());
      } 
      return property.getConstraint();
   }
   
   @Override
   public Value get(Scope scope, Module left) {
      Property property = reference.get();
      
      if(property == null) {
         Scope inner = left.getScope();
         State state = inner.getState();
         Value value = state.get(name);
         
         if(value == null) {
            Type type = left.getType(name);
            
            if(type != null) {
               return Value.getTransient(type);
            }
            Property match = resolver.matchFromModule(scope, left, name);
            
            if(match != null) {
               reference.set(match);
               return new PropertyValue(match, left, name);
            }
         }
         return value;
      } 
      return new PropertyValue(property, left, name);
   }
   
}