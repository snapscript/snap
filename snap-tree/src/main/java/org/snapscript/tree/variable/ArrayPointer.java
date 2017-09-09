package org.snapscript.tree.variable;

import static org.snapscript.core.Reserved.PROPERTY_LENGTH;

import java.lang.reflect.Array;

import org.snapscript.core.Scope;
import org.snapscript.core.Value;

public class ArrayPointer implements VariablePointer<Object> {
   
   private final ObjectPointer pointer;
   private final String name;
   
   public ArrayPointer(ConstantResolver resolver, String name) {
      this.pointer = new ObjectPointer(resolver, name);
      this.name = name;
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