package org.snapscript.core.variable.index;

import static org.snapscript.core.Reserved.PROPERTY_LENGTH;
import static org.snapscript.core.Reserved.TYPE_CLASS;
import static org.snapscript.core.constraint.Constraint.INTEGER;
import static org.snapscript.core.constraint.Constraint.TYPE;

import java.lang.reflect.Array;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;
import org.snapscript.core.variable.bind.VariableFinder;

public class ArrayPointer implements VariablePointer<Object> {
   
   private final TypeInstancePointer pointer;
   private final String name;
   
   public ArrayPointer(VariableFinder finder, String name) {
      this.pointer = new TypeInstancePointer(finder, name);
      this.name = name;
   }

   @Override
   public Constraint compile(Scope scope, Constraint left) {
      if(name.equals(PROPERTY_LENGTH)) {
         return INTEGER;
      }
      if(name.equals(TYPE_CLASS)) {
         return TYPE;
      }
      return pointer.compile(scope, left);
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