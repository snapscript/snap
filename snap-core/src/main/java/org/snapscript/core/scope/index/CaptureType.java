package org.snapscript.core.scope.index;

public enum CaptureType {
   GLOBALS(true, false, true),
   CLOSURE(false, true, false),
   MEMBER(false, true, false),
   GENERICS(false, true, false),
   SUPER(true, false, false),
   TEMPLATE(true, true, false),
   EVALUATE(true, true, false);

   private final boolean reference;
   private final boolean extension;
   private final boolean globals;

   private CaptureType(boolean reference, boolean extension, boolean globals) {
      this.reference = reference;
      this.extension = extension;
      this.globals = globals;
   }

   public boolean isReference() {
      return reference;
   }

   public boolean isExtension() {
      return extension;
   }

   public boolean isGlobals() {
      return globals;
   }
}
