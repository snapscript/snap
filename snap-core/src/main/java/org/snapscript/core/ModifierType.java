package org.snapscript.core;

public enum ModifierType {
   STATIC("static", 0x0001),
   OVERRIDE("override", 0x0002),
   PRIVATE("private", 0x0004),
   PUBLIC("public", 0x0008),
   CONSTANT("const", 0x0010),
   VARIABLE("var", 0x0020),
   ABSTRACT("abstract", 0x040),
   VARARGS("...", 0x080);
   
   public final String token;
   public final int mask;
   
   private ModifierType(String token, int mask) {
      this.token = token;
      this.mask = mask;
   }
   
   public static boolean isStatic(int modifier){
      return (STATIC.mask & modifier) != 0;
   }
   
   public static boolean isConstant(int modifier){
      return (CONSTANT.mask & modifier) != 0;
   }  
   
   public static boolean isOverride(int modifier) {
      return (OVERRIDE.mask & modifier) != 0;
   }
   
   public static boolean isAbstract(int modifier) {
      return (ABSTRACT.mask & modifier) != 0;
   }
   
   public static boolean isPublic(int modifier) {
      return (PUBLIC.mask & modifier) != 0;
   }
   
   public static boolean isPrivate(int modifier) {
      return (PRIVATE.mask & modifier) != 0;
   }
   
   public static boolean isVariableArgument(int modifier) {
      return (VARARGS.mask & modifier) != 0;
   }
   
   public static ModifierType resolveModifier(String token) {
      ModifierType[] modifiers = ModifierType.values();
      
      for(ModifierType modifier : modifiers){
         if(modifier.token.equals(token)) {
            return modifier;
         }
      }
      return null;
   }
}
