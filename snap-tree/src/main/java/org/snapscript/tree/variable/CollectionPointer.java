package org.snapscript.tree.variable;

import static org.snapscript.core.Reserved.PROPERTY_LENGTH;

import java.util.Collection;

import org.snapscript.core.Constraint;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;

public class CollectionPointer implements VariablePointer<Collection> {
   
   private final ObjectPointer pointer;
   private final String name;
   
   public CollectionPointer(ConstantResolver resolver, String name) {
      this.pointer = new ObjectPointer(resolver, name);
      this.name = name;
   }

   @Override
   public Constraint check(Scope scope, Type left) {
      if(name.equals(PROPERTY_LENGTH)) {
         return Constraint.getInstance(scope, Integer.class);
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