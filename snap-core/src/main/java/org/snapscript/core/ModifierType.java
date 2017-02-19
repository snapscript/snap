/*
 * ModifierType.java December 2016
 *
 * Copyright (C) 2016, Niall Gallagher <niallg@users.sf.net>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
 * implied. See the License for the specific language governing 
 * permissions and limitations under the License.
 */

package org.snapscript.core;

public enum ModifierType {
   STATIC("static", 0x0001),
   OVERRIDE("override", 0x0002),
   PRIVATE("private", 0x0004),
   PUBLIC("public", 0x0008),
   PROTECTED("protected", 0x0010), // java only
   CONSTANT("const", 0x0020),
   VARIABLE("var", 0x0040),
   ABSTRACT("abstract", 0x080),
   VARARGS("...", 0x100);
   
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
      ModifierType[] modifiers = ModifierType.values();
      
      for(ModifierType modifier : modifiers){
         if(modifier.token.equals(token)) {
            return modifier;
         }
      }
      return null;
   }
}
