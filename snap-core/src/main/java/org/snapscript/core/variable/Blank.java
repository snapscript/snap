package org.snapscript.core.variable;

import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class Blank extends Value {
   
   private final AtomicReference<Object> reference;
   private final Constraint type;
   private final int modifiers;
   
   public Blank(Object value, Constraint type, int modifiers) {
      this.reference = new AtomicReference<Object>(value);
      this.modifiers = modifiers;
      this.type = type;
   }
   
   @Override
   public boolean isConstant() {
      return reference.get() != null;
   }
   
   @Override
   public boolean isProperty() {
      return modifiers != -1;
   }
   
   @Override
   public int getModifiers() {
      return modifiers;
   }
   
   @Override
   public Type getType(Scope scope) {
      return type.getType(scope);
   }
   
   @Override
   public <T> T getValue() {
      return (T)reference.get();
   }
   
   @Override
   public void setValue(Object value){
      if(!reference.compareAndSet(null, value)) {
         throw new InternalStateException("Illegal modification of constant");
      }
   } 
   
   @Override
   public String toString() {
      return String.valueOf(reference);
   }
}