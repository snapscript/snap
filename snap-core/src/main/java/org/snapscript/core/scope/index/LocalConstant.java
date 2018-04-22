package org.snapscript.core.scope.index;

import java.util.List;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class LocalConstant extends Local {

   private final Constraint type;
   private final Object value;
   private final String name;
   
   public LocalConstant(Object value, String name) {
      this(value, name, null);
   }

   public LocalConstant(Object value, String name, Constraint type) {
      this.value = value;
      this.name = name;
      this.type = type;
   }
   
   @Override
   public boolean isConstant(){
      return true;
   }
   
   @Override
   public List<Constraint> getGenerics(Scope scope) {
      return type.getGenerics(scope);
   }
   
   @Override
   public Type getType(Scope scope) {
      return type.getType(scope);
   }
   
   @Override
   public String getName(Scope scope) {
      return name;
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