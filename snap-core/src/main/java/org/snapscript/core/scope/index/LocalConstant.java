package org.snapscript.core.scope.index;

import static org.snapscript.core.constraint.Constraint.NONE;

import org.snapscript.core.Entity;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.InternalStateException;

public class LocalConstant extends Local {

   private final Constraint constraint;
   private final Entity source;
   private final Object value;
   private final String name;
   
   public LocalConstant(Object value, Entity source, String name) {
      this(value, source, name, NONE);
   }

   public LocalConstant(Object value, Entity source, String name, Constraint constraint) {
      if(source == null){
         throw new IllegalStateException();
      }
      this.constraint = constraint;
      this.source = source;
      this.value = value;
      this.name = name;
   }
   
   @Override
   public boolean isConstant(){
      return true;
   }
   
   @Override
   public Entity getSource() {
      return source;
   }
   
   @Override
   public Constraint getConstraint() {
      return constraint;
   }
   
   @Override
   public String getName() {
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