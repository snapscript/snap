package org.snapscript.core.variable;

import static org.snapscript.core.ModifierType.CONSTANT;
import static org.snapscript.core.constraint.Constraint.NONE;

import java.util.List;

import org.snapscript.core.Entity;
import org.snapscript.core.constraint.ClassConstraint;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.type.Type;

public class Constant extends Value {   
   
   public static final Constraint NUMBER = new ClassConstraint(Number.class, CONSTANT.mask);
   public static final Constraint INTEGER = new ClassConstraint(Integer.class, CONSTANT.mask);
   public static final Constraint LONG = new ClassConstraint(Long.class, CONSTANT.mask);
   public static final Constraint FLOAT = new ClassConstraint(Float.class, CONSTANT.mask);
   public static final Constraint DOUBLE = new ClassConstraint(Double.class, CONSTANT.mask);
   public static final Constraint SHORT = new ClassConstraint(Short.class, CONSTANT.mask);
   public static final Constraint BYTE = new ClassConstraint(Byte.class, CONSTANT.mask);
   public static final Constraint STRING = new ClassConstraint(String.class, CONSTANT.mask);
   public static final Constraint BOOLEAN = new ClassConstraint(Boolean.class, CONSTANT.mask);
   public static final Constraint CHARACTER = new ClassConstraint(Character.class, CONSTANT.mask);
   public static final Constraint LIST = new ClassConstraint(List.class, CONSTANT.mask);
   public static final Constraint TYPE = new ClassConstraint(Type.class, CONSTANT.mask);
   
   private final Constraint constraint;
   private final Entity source;
   private final Data value;
   private final int modifiers;
   
   public Constant(Object value, Entity source) {
      this(value, source, NONE);
   }

   public Constant(Object value, Entity source, Constraint constraint) {
      this(value, source, constraint, 0);
   }
   
   public Constant(Object value, Entity source, Constraint constraint, int modifiers) {
      if(source == null){
         throw new IllegalStateException();
      }
      this.value = new ValueData(value, source);
      this.constraint = constraint;
      this.modifiers = modifiers;
      this.source = source;     
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
   public <T> T getValue() {
      return value.getValue();
   }
   
   @Override
   public void setData(Data value){
      throw new InternalStateException("Illegal modification of constant");
   } 
   
   @Override
   public String toString() {
      return String.valueOf(value);
   }
}