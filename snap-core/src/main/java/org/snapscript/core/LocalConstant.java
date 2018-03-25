package org.snapscript.core;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Type;

public class LocalConstant extends Local {

   private final Object value;
   private final Type type;
   private final String name;
   
   public LocalConstant(Object value, String name) {
      this(value, name, null);
   }

   public LocalConstant(Object value, String name, Type type) {
      this.value = value;
      this.name = name;
      this.type = type;
   }
   
   @Override
   public String getName() {
      return name;
   }
   
   @Override
   public Type getConstraint() {
      return type;
   }
   
   @Override
   public <T> T getValue() {
      return (T)value;
   }
   
   @Override
   public void setValue(Object value){
      throw new InternalStateException("Illegal modification of constant");
   } 
   
   @Override
   public String toString() {
      return String.format("%s: %s", name, value);
   }
}