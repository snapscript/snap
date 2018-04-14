package org.snapscript.tree.variable.index;

import static org.snapscript.core.Reserved.PROPERTY_LENGTH;
import static org.snapscript.core.constraint.Constraint.INTEGER;

import java.util.Collection;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.Value;
import org.snapscript.tree.variable.VariableFinder;

public class CollectionPointer implements VariablePointer<Collection> {
   
   private final TypeInstancePointer pointer;
   private final String name;
   
   public CollectionPointer(VariableFinder finder, String name) {
      this.pointer = new TypeInstancePointer(finder, name);
      this.name = name;
   }

   @Override
   public Constraint compile(Scope scope, Constraint left) {
      if(name.equals(PROPERTY_LENGTH)) {
         return INTEGER;
      }
      return pointer.compile(scope, left);
   }
   
   @Override
   public Value get(Scope scope, Collection left) {
      if(name.equals(PROPERTY_LENGTH)) {
         int length = left.size();
         return Value.getConstant(length);
      }
      return pointer.get(scope, left);
   }
}