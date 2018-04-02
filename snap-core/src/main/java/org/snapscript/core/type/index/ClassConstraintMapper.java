package org.snapscript.core.type.index;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.snapscript.core.PrimitivePromoter;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.type.Any;

public class ClassConstraintMapper {
   
   private final PrimitivePromoter promoter;
   
   public ClassConstraintMapper() {
      this.promoter = new PrimitivePromoter();
   }
   
   public Constraint map(Field field, int modifiers) {
      Class type = field.getType();   
      Class real = map(type);
      
      return Constraint.getConstraint(real, modifiers);
   }
   
   public Constraint map(Method method, int modifiers) {
      Class type = method.getReturnType();    
      Class real = map(type);
      
      return Constraint.getConstraint(real);        
   }
   
   private Class map(Class type) {
      if(type == Object.class) {
         return null;
      }
      if(type == Any.class){
         return null;
      }
      if(type == void.class){
         return null;
      }
      return promoter.promote(type);
   }
}
