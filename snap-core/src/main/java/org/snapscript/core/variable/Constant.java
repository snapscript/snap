package org.snapscript.core.variable;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class Constant extends Value {
   
   private final Constraint type;
   private final Object value;
   private final int modifiers;
   
   public Constant(Object value) {
      this(value, NONE);
   }

   public Constant(Object value, Constraint type) {
      this(value, type, 0);
   }
   
   public Constant(Object value, Constraint type, int modifiers) {
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
      return type.getType(scope);
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