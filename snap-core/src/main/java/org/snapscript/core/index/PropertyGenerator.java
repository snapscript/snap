package org.snapscript.core.index;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.ModifierType;
import org.snapscript.core.Type;
import org.snapscript.core.function.AccessorProperty;
import org.snapscript.core.property.Property;

public class PropertyGenerator {
  
   public PropertyGenerator(){
      super();
   }
   
   public Property generate(Field field, Type type, Type constraint, String name, int modifiers) {
      try {
         if(ModifierType.isConstant(modifiers)) {
            FinalFieldAccessor accessor = new FinalFieldAccessor(field);
            
            if(!field.isAccessible()) {
               field.setAccessible(true);
            }
            return new AccessorProperty(name, type, constraint, accessor, modifiers); 
         }
         FieldAccessor accessor = new FieldAccessor(field);
         
         if(!field.isAccessible()) {
            field.setAccessible(true);
         }
         return new AccessorProperty(name, type, constraint, accessor, modifiers); 
      } catch(Exception e) {
         throw new InternalStateException("Could not create property from " + field);
      }
   }
   
   public Property generate(Method read, Method write, Type type, Type constraint, String name, int modifiers) {
      try {
         MethodAccessor accessor = new MethodAccessor(type, read, write);
         
         if(read.isAccessible()) {
            read.setAccessible(true);
         }
         if(write != null) {
            if(!write.isAccessible()) {
               write.setAccessible(true);
            }
         }
         return new AccessorProperty(name, type, constraint, accessor, modifiers);  
      } catch(Exception e) {
         throw new InternalStateException("Could not create property from " + read);
      }
   }
}
