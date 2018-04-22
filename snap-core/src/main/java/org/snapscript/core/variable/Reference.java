package org.snapscript.core.variable;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class Reference extends Value {
   
   private Constraint type;
   private Object value;
   private int modifiers;
   
   public Reference(Object value) {
      this(value, NONE);
   }
   
   public Reference(Object value, Constraint type) {
      this(value, type, -1);
   }
   
   public Reference(Object value, Constraint type, int modifiers) {
      this.modifiers = modifiers;
      this.value = value;
      this.type = type; 
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
      return type.getType(scope);
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
      return String.valueOf(value);
   }
}