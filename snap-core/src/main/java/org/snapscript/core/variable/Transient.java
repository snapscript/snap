package org.snapscript.core.variable;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class Transient extends Value {
   
   private final Constraint type;
   private final Object object;
   
   public Transient(Object object) {
      this(object, NONE);
   }
   
   public Transient(Object object, Constraint type) {
      this.object = object;
      this.type = type;
   }
   
   @Override
   public Type getType(Scope scope){
      return type.getType(scope);
   }
   
   @Override
   public <T> T getValue(){
      return (T)object;
   }
   
   @Override
   public void setValue(Object value){
      throw new InternalStateException("Illegal modification of transient");
   } 
   
   @Override
   public String toString() {
      return String.valueOf(object);
   }
}