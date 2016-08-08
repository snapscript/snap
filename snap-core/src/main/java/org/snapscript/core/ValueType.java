package org.snapscript.core;

public enum ValueType {
   CONSTANT,
   REFERENCE,
   TRANSIENT,
   BLANK,
   PROPERTY;
   
   public static Value getBlank() {
      return new Blank();
   }
   
   public static Value getBlank(Type type) {
      return new Blank(type);
   }
   
   public static Value getConstant(Object value) {
      return new Constant(value);
   }
   
   public static Value getConstant(Object value, Type type) {
      return new Constant(value, type);
   }
   
   public static Value getReference(Object value) {
      return new Reference(value);
   }
   
   public static Value getReference(Object value, Type type) {
      return new Reference(value, type);
   }
   
   public static Value getProperty(Object value, Type type) {
      return new Reference(value, type, true);
   }
   
   public static Value getTransient(Object value) {
      return new Transient(value);
   }
   
   public static Value getTransient(Object value, Type type) {
      return new Transient(value, type);
   }
}
