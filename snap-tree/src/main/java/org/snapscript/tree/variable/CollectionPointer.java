package org.snapscript.tree.variable;

import static org.snapscript.core.Reserved.PROPERTY_LENGTH;

import java.util.Collection;

import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.constraint.Constraint;

public class CollectionPointer implements VariablePointer<Collection> {
   
   private final ObjectPointer pointer;
   private final String name;
   
   public CollectionPointer(VariableFinder finder, String name) {
      this.pointer = new ObjectPointer(finder, name);
      this.name = name;
   }

   @Override
   public Constraint check(Scope scope, Constraint left) {
      if(name.equals(PROPERTY_LENGTH)) {
         return Constraint.INTEGER;
      }
      return pointer.check(scope, left);
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