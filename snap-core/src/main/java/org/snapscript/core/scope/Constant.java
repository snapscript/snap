package org.snapscript.core.scope;

import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.type.Type;

public class Constant extends Value {
   
   private final Object value;
   private final Type type;
   private final int modifiers;
   
   public Constant(Object value) {
      this(value, null);
   }

   public Constant(Object value, Type type) {
      this(value, type, 0);
   }
   
   public Constant(Object value, Type type, int modifiers) {
      this.modifiers = modifiers;
      this.value = value;
      this.type = type;
   }
   
   @Override
   public boolean isConstant() {
      return true;
   }
   
   @Override
   public boolean isProperty(){
      return modifiers != -1;
   }
   
   @Override
   public int getModifiers(){
      return modifiers;
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
   public void setValue(Object value){
      throw new InternalStateException("Illegal modification of constant");
   } 
   
   @Override
   public String toString() {
      return String.valueOf(value);
   }
}