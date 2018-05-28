package org.snapscript.core;

public enum ModifierType {
   VARIABLE(0x0001, "var", "let"),
   CONSTANT(0x0002, "const"),
   FUNCTION(0x0004, "function", "func"),  
   CLASS(0x0008, "class"),   
   TRAIT(0x0010, "trait"),
   ENUM(0x0020, "enum"),
   MODULE(0x0040, "module"),
   OVERRIDE(0x0080, "override"),
   PRIVATE(0x0100, "private"),
   PUBLIC(0x0200, "public"),
   PROTECTED(0x0400, "protected"), // java only
   STATIC(0x0800, "static"),
   ABSTRACT(0x1000, "abstract"),
   PROXY(0x2000, "proxy"),
   ARRAY(0x4000, "[]"),
   VARARGS(0x8000, "...");
   
   public final String[] tokens;
   public final int mask;
   
   private ModifierType(int mask, String... tokens) {
      this.tokens = tokens;
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
            for(int i = 0; i < modifier.tokens.length; i++) {
               if(modifier.tokens[i].equals(token)) {
                  return modifier;
               }
            }
         }
      }
      return null;
   }
   
   private static final ModifierType[] VALUES = {
      VARIABLE,
      CONSTANT,
      FUNCTION, 
      CLASS,
      TRAIT,
      ENUM,
      MODULE,
      OVERRIDE,
      PRIVATE,
      PUBLIC,
      PROTECTED,
      STATIC,
      ABSTRACT,
   };
}