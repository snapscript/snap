package org.snapscript.core.variable.index;

import static org.snapscript.core.Reserved.PROPERTY_LENGTH;
import static org.snapscript.core.Reserved.TYPE_CLASS;
import static org.snapscript.core.constraint.Constraint.INTEGER;
import static org.snapscript.core.constraint.Constraint.TYPE;

import java.lang.reflect.Array;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;
import org.snapscript.core.variable.bind.VariableFinder;

public class ArrayPointer implements VariablePointer {
   
   private final TypeInstancePointer pointer;
   private final String name;
   
   public ArrayPointer(VariableFinder finder, String name) {
      this.pointer = new TypeInstancePointer(finder, name);
      this.name = name;
   }

   @Override
   public Constraint getConstraint(Scope scope, Constraint left) {
      if(name.equals(PROPERTY_LENGTH)) {
         return INTEGER;
      }
      if(name.equals(TYPE_CLASS)) {
         return TYPE;
      }
      return pointer.getConstraint(scope, left);
   }

   @Override
   public Value getValue(Scope scope, Value left) {
      if(name.equals(PROPERTY_LENGTH)) {
         Module module = scope.getModule();
         Object array = left.getValue();
         int length = Array.getLength(array);
         
         return Value.getConstant(length, module);
      }
      return pointer.getValue(scope, left);
   }
}