package org.snapscript.core.index;

import static org.snapscript.core.ModifierType.ABSTRACT;
import static org.snapscript.core.ModifierType.CONSTANT;
import static org.snapscript.core.ModifierType.PRIVATE;
import static org.snapscript.core.ModifierType.PUBLIC;
import static org.snapscript.core.ModifierType.STATIC;
import static org.snapscript.core.ModifierType.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ModifierConverter {
   
   public int convert(Method method) {
      int result = PRIVATE.mask;
      
      if(method != null) {
         int mask = method.getModifiers();
   
         if(method.isVarArgs()) {
            result |= VARARGS.mask;
         }
         if(Modifier.isAbstract(mask)) {
            result |= ABSTRACT.mask;
         }
         if(Modifier.isFinal(mask)) {
            result |= CONSTANT.mask;
         }
         if(Modifier.isPrivate(mask)) {
            result |= PRIVATE.mask;
         }
         if(Modifier.isProtected(mask)) {
            result |= PROTECTED.mask;
            result &= ~PRIVATE.mask;
         }
         if(Modifier.isPublic(mask)) {
            result |= PUBLIC.mask;
            result &= ~PRIVATE.mask;
         }
         if(Modifier.isStatic(mask)) {
            result |= STATIC.mask;
         }
         if(!Modifier.isPublic(mask) && Modifier.isPrivate(mask) && Modifier.isProtected(mask)) {
            result |= PRIVATE.mask;
         }
      }
      return result;
   }
   
   public int convert(Constructor constructor) {
      int result = PRIVATE.mask;
      
      if(constructor != null) {
         int modifiers = constructor.getModifiers();
   
         if(constructor.isVarArgs()) {
            result |= VARARGS.mask;
         }
         if(Modifier.isAbstract(modifiers)) {
            result |= ABSTRACT.mask;
         }
         if(Modifier.isFinal(modifiers)) {
            result |= CONSTANT.mask;
         }
         if(Modifier.isPrivate(modifiers)) {
            result |= PRIVATE.mask;
         }
         if(Modifier.isPublic(modifiers)) {
            result |= PUBLIC.mask;
            result &= ~PRIVATE.mask;
         }
         if(Modifier.isProtected(modifiers)) {
            result |= PROTECTED.mask;
            result &= ~PRIVATE.mask;
         }
         result |= STATIC.mask;
      }
      return result;
   }
   
   public int convert(Field field) {
      int result = PRIVATE.mask; // default is private
      
      if(field != null) {
         int modifiers = field.getModifiers();
         
         if(Modifier.isFinal(modifiers)) {
            result |= CONSTANT.mask;
         }
         if(Modifier.isPrivate(modifiers)) {
            result |= PRIVATE.mask;
         }
         if(Modifier.isPublic(modifiers)) {
            result |= PUBLIC.mask;
            result &= ~PRIVATE.mask;
         }
         if(Modifier.isProtected(modifiers)) {
            result |= PROTECTED.mask;
            result &= ~PRIVATE.mask;
         }
         if(Modifier.isStatic(modifiers)) {
            result |= STATIC.mask;
         }
      }
      return result;
   }
}
