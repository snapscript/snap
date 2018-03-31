package org.snapscript.tree.variable;

import static org.snapscript.core.Reserved.PROPERTY_LENGTH;
import static org.snapscript.core.Reserved.TYPE_CLASS;

import java.lang.reflect.Array;

import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.constraint.Constraint;

public class ArrayPointer implements VariablePointer<Object> {
   
   private final ObjectPointer pointer;
   private final String name;
   
   public ArrayPointer(VariableFinder finder, String name) {
      this.pointer = new ObjectPointer(finder, name);
      this.name = name;
   }

   @Override
   public Constraint check(Scope scope, Constraint left) {
      if(name.equals(PROPERTY_LENGTH)) {
         return Constraint.INTEGER;
      }
      if(name.equals(TYPE_CLASS)) {
         return Constraint.TYPE;
      }
      return pointer.check(scope, left);
   }

   @Override
   public Value get(Scope scope, Object left) {
      if(name.equals(PROPERTY_LENGTH)) {
         int length = Array.getLength(left);
         return Value.getConstant(length);
      }
      return pointer.get(scope, left);
   }
}