package org.snapscript.core.type;

public enum Category {
   CLASS(0),
   TRAIT(1),
   ENUM(2),
   FUNCTION(3),
   ARRAY(4),
   PROXY(5),
   MODULE(6);
   
   public final int index;
   
   private Category(int index) {
      this.index = index;
   }
   
   public boolean isEnum() {
      return this == ENUM;
   }
   
   public boolean isClass(){
      return this == CLASS;
   }

   public boolean isModule(){
      return this == MODULE;
   }
   
   public boolean isTrait() {
      return this == TRAIT;
   }
   
   public boolean isFunction(){
      return this == FUNCTION;
   }
   
   public boolean isArray(){
      return this == ARRAY;
   }
   
   public boolean isProxy(){
      return this == PROXY;
   }
}