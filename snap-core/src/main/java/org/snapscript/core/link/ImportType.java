package org.snapscript.core.link;

public enum ImportType {
   IMPLICIT(false),
   EXPLICIT(true);
   
   public final boolean required;

   private ImportType(boolean required){
      this.required = required;
   }
   
   public boolean isRequired() {
      return required;
   }
}
