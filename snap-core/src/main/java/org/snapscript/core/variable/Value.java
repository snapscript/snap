package org.snapscript.core.variable;

import static org.snapscript.core.constraint.Constraint.NONE;

import org.snapscript.core.Entity;
import org.snapscript.core.attribute.Attribute;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.type.Type;

public abstract class Value implements Attribute {
   
   public static final Value NULL = new Null();
   
   public static Value getNull() {
      return new Null();
   }
   
   public static Value getConstant(Object value, Entity source) {
      return new Constant(value, source);
   }
   
   public static Value getConstant(Object value, Entity source, Constraint type) {
      return new Constant(value, source, type);
   }
   
   public static Value getConstant(Object value, Entity source, Constraint type, int modifiers) {
      return new Constant(value, source, type, modifiers);
   }
   
   public static Value getReference(Object value, Entity source) {
      return new Reference(value, source);
   }
   
   public static Value getReference(Object value, Entity source, Constraint type) {
      return new Reference(value, source, type);
   }
   
   public static Value getProperty(Object value, Entity source, Constraint type, int modifiers) {
      return new Reference(value, source, type, modifiers);
   }
   
   public static Value getBlank(Object value, Entity source, Constraint type, int modifiers) {
      return new Blank(value, source, type, modifiers);
   }
   
   public static Value getTransient(Object value, Entity source) {
      return new Transient(value, source);
   }
   
   public static Value getTransient(Object value, Entity source, Constraint type) {
      return new Transient(value, source, type);
   }

   public double getDouble() {
      Number number = getNumber();

      if (number != null) {
         return number.doubleValue();
      }
      return 0;
   }

   public long getLong() {
      Number number = getNumber();

      if (number != null) {
         return number.longValue();
      }
      return 0;
   }

   public int getInteger() {
      Number number = getNumber();

      if (number != null) {
         return number.intValue();
      }
      return 0;
   }

   public Number getNumber() {
      Object value = getValue();

      if (value != null) {
         return ValueMapper.toNumber(value); 
      }
      return null;
   }
   
   public char getCharacter() {
      Object value = getValue();

      if (value != null) {
         return ValueMapper.toCharacter(value); 
      }
      return 0;
   }
   
   public String getString() {
      Object value = getValue();

      if (value != null) {
         return value.toString();
      }
      return null;
   }      
   
   public Constraint getConstraint(){
      return NONE; 
   }   
   
   public boolean isProperty() {
      return false;
   }
   
   public boolean isConstant() {
      return false;
   }
   
   public int getModifiers(){
      return -1;
   }

   public Data getData() {
      return DataMapper.toData(this);
   }
   
   public String getName() {
      return null;
   }
   
   public Type getHandle() {
      return null;
   }   
   
   public Type getType() {
      Object value = getValue();

      if (value != null) {
         return getSource().getScope().getModule().getContext().getExtractor().getType(value);
      }
      return null;
   }  
   
   public abstract <T> T getValue();
   public abstract void setValue(Object value);
   
   
}