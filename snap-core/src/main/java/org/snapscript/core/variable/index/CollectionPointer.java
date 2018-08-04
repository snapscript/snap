package org.snapscript.core.variable.index;

import static org.snapscript.core.Reserved.PROPERTY_LENGTH;
import static org.snapscript.core.constraint.Constraint.INTEGER;

import java.util.Collection;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;
import org.snapscript.core.variable.bind.VariableFinder;

public class CollectionPointer implements VariablePointer<Collection> {
   
   private final TypeInstancePointer pointer;
   private final String name;
   
   public CollectionPointer(VariableFinder finder, String name) {
      this.pointer = new TypeInstancePointer(finder, name);
      this.name = name;
   }

   @Override
   public Constraint getConstraint(Scope scope, Constraint left) {
      if(name.equals(PROPERTY_LENGTH)) {
         return INTEGER;
      }
      return pointer.getConstraint(scope, left);
   }
   
   @Override
   public Value getValue(Scope scope, Collection left) {
      if(name.equals(PROPERTY_LENGTH)) {
         Module module = scope.getModule();
         int length = left.size();
         
         return Value.getConstant(length, module);
      }
      return pointer.getValue(scope, left);
   }
}