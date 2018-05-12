package org.snapscript.core.variable.index;

import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;
import org.snapscript.core.variable.bind.VariableFinder;
import org.snapscript.core.variable.bind.VariableResult;

public class TypeStaticPointer implements VariablePointer<Type> {
   
   private final AtomicReference<VariableResult> reference;
   private final VariableFinder finder;
   private final String name;
   
   public TypeStaticPointer(VariableFinder finder, String name) {
      this.reference = new AtomicReference<VariableResult>();
      this.finder = finder;
      this.name = name;
   }

   @Override
   public Constraint getConstraint(Scope scope, Constraint left) {
      VariableResult property = reference.get();
      
      if(property == null) {
         Type type = left.getType(scope);
         VariableResult match = finder.findAll(scope, type, name);
         
         if(match != null) {
            reference.set(match);
            return match.getConstraint(left);
         }
         return null;
      } 
      return property.getConstraint(left);
   }
   
   @Override
   public Value get(Scope scope, Type left) {
      VariableResult property = reference.get();
      
      if(property == null) {
         VariableResult match = finder.findAll(scope, left, name);
         
         if(match == null) {
            match = finder.findAll(scope, (Object)left, name); // find on the type
         }         
         if(match != null) {
            reference.set(match);
            return match.getValue(left);
         }
         return null;
      } 
      return property.getValue(left);
   }   
}