package org.snapscript.core;

import org.snapscript.core.Type;

public class LocalReference extends Local {

   private Object value;
   private String name;
   private Type type;
   
   public LocalReference(Object value, String name) {
      this(value, name, null);
   }
   
   public LocalReference(Object value, String name, Type type) {
      this.value = value;
      this.name = name;
      this.type = type; 
   }
   
   @Override
   public String getName(){
      return name;
   }
   
   @Override
   public Type getType(Scope scope) {
      return type;
   }
   
   @Override
   public <T> T getValue() {
      return (T)value;
   }

   @Override
   public void setValue(Object value) {
      this.value = value;
   }
   
   @Override
   public String toString() {
      return String.format("%s: %s", name, value);
   }
}