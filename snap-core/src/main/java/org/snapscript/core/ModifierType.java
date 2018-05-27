package org.snapscript.core;

public enum ModifierType {
   STATIC("static", 0x0001),
   OVERRIDE("override", 0x0002),
   PRIVATE("private", 0x0004),
   PUBLIC("public", 0x0008),
   PROTECTED("protected", 0x0010), // java only
   CONSTANT("const", 0x0020),
   VARIABLE("var", 0x0040),
   MODULE("module", 0x0080),
   CLASS("class", 0x0100),   
   TRAIT("trait", 0x0200),
   ENUM("enum", 0x0400),
   FUNCTION("function", 0x0800),   
   ABSTRACT("abstract", 0x1000),
   PROXY("proxy", 0x2000),
   ARRAY("[]", 0x4000),
   VARARGS("...", 0x8000);
   
   public final String token;
   public final int mask;
   
   private ModifierType(String token, int mask) {
      this.token = token;
      this.mask = mask;
   }
   
   public static boolean isDefault(int modifier){
      return modifier == -1;
   }
   
   public static boolean isStatic(int modifier){
      return modifier >= 0 && (STATIC.mask & modifier) != 0;
   }
   
   public static boolean isModule(int modifier){
      return modifier >= 0 && (MODULE.mask & modifier) != 0;
   }
   
   public static boolean isClass(int modifier){
      return modifier >= 0 && (CLASS.mask & modifier) != 0;
   }
   
   public static boolean isTrait(int modifier){
      return modifier >= 0 && (TRAIT.mask & modifier) != 0;
   }
   
   public static boolean isEnum(int modifier){
      return modifier >= 0 && (ENUM.mask & modifier) != 0;
   }
   
   public static boolean isProxy(int modifier){
      return modifier >= 0 && (PROXY.mask & modifier) != 0;
   }
   
   public static boolean isFunction(int modifier){
      return modifier >= 0 && (FUNCTION.mask & modifier) != 0;
   }
   
   public static boolean isArray(int modifier){
      return modifier >= 0 && (ARRAY.mask & modifier) != 0;
   }
   
   public static boolean isConstant(int modifier){
      return modifier >= 0 && (CONSTANT.mask & modifier) != 0;
   }  
   
   public static boolean isVariable(int modifier){
      return modifier >= 0 && (VARIABLE.mask & modifier) != 0;
   }  
   
   public static boolean isOverride(int modifier) {
      return modifier >= 0 && (OVERRIDE.mask & modifier) != 0;
   }
   
   public static boolean isAbstract(int modifier) {
      return modifier >= 0 && (ABSTRACT.mask & modifier) != 0;
   }
   
   public static boolean isPublic(int modifier) {
      return modifier >= 0 && (PUBLIC.mask & modifier) != 0;
   }
   
   public static boolean isPrivate(int modifier) {
      return modifier >= 0 && (PRIVATE.mask & modifier) != 0;
   }
   
   public static boolean isProtected(int modifier) {
      return modifier >= 0 && (PROTECTED.mask & modifier) != 0;
   }
   
   public static boolean isVariableArgument(int modifier) {
      return modifier >= 0 && (VARARGS.mask & modifier) != 0;
   }
   
   public static ModifierType resolveModifier(String token) {
      if(token != null) {
         for(ModifierType modifier : VALUES){
            if(modifier.token.equals(token)) {
               return modifier;
            }
         }
      }
      return null;
   }
   
   private static final ModifierType[] VALUES = {
      STATIC,
      OVERRIDE,
      PRIVATE,
      PUBLIC,
      PROTECTED,
      CONSTANT,
      VARIABLE,
      ABSTRACT,
      VARARGS
   };
}