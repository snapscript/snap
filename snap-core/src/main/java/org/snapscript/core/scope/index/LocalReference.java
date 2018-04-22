package org.snapscript.core.scope.index;

import java.util.List;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class LocalReference extends Local {

   private Constraint type;
   private Object value;
   private String name;
   
   public LocalReference(Object value, String name) {
      this(value, name, null);
   }
   
   public LocalReference(Object value, String name, Constraint type) {
      this.value = value;
      this.name = name;
      this.type = type; 
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
   public void setValue(Object value) {
      this.value = value;
   }
   
   @Override
   public String toString() {
      return String.format("%s: %s", name, value);
   }
}