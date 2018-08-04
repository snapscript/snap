package org.snapscript.core.scope.index;

import static org.snapscript.core.constraint.Constraint.NONE;

import org.snapscript.core.Entity;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.variable.Data;
import org.snapscript.core.variable.ValueData;

public class LocalConstant extends Local {

   private final Constraint constraint;
   private final Entity source;
   private final Data value;
   private final String name;
   
   public LocalConstant(Object object, Entity source, String name) {
      this(object, source, name, NONE);
   }

   public LocalConstant(Object object, Entity source, String name, Constraint constraint) {
      if(source == null){
         throw new IllegalStateException();
      }
      this.value = new ValueData(object, source);
      this.constraint = constraint;
      this.source = source;
      this.name = name;
   }
   
   @Override
   public boolean isConstant(){
      return true;
   }
   
   @Override
   public Data getData() {
      return value;
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
      return value.getValue();
   }
   
   @Override
   public void setData(Data value){
      throw new InternalStateException("Illegal modification of constant");
   } 
   
   @Override
   public String toString() {
      return String.format("%s: %s", name, value);
   }
}