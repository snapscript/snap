package org.snapscript.core.variable.index;

import static org.snapscript.core.ModifierType.CONSTANT;

import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.module.Module;
import org.snapscript.core.property.Property;
import org.snapscript.core.property.PropertyValue;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;
import org.snapscript.core.variable.bind.VariableFinder;

public class ModulePointer implements VariablePointer<Module> {
   
   private final AtomicReference<Property> reference;
   private final VariableFinder finder;
   private final String name;
   
   public ModulePointer(VariableFinder finder, String name) {
      this.reference = new AtomicReference<Property>();
      this.finder = finder;
      this.name = name;
   }

   @Override
   public Constraint compile(Scope scope, Constraint left) {
      Property property = reference.get();
      
      if(property == null) {
         Type parent = left.getType(scope);
         Module module = parent.getModule();
         State state = scope.getState();
         Value value = state.get(name);
         
         if(value == null) {
            Type type = module.getType(name);
            
            if(type == null) {
               Property match = finder.findAll(scope, module, name);
               
               if(match != null) {
                  reference.set(match);
                  return match.getConstraint();
               }
               return null;
            }
            return Constraint.getConstraint(type, CONSTANT.mask);
         }
         return value;
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
            
            if(type == null) {            
               Property match = finder.findAll(scope, left, name);
               
               if(match != null) {
                  reference.set(match);
                  return new PropertyValue(match, left, name);
               }
               return null;
            }
            return Value.getTransient(type);
         }
         return value;
      } 
      return new PropertyValue(property, left, name);
   }
   
}