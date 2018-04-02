package org.snapscript.core.type.index;

import static org.snapscript.core.constraint.Constraint.NONE;

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
   
   public Constraint toConstraint(Field field, int modifiers) {
      if(field != null) {
         Class type = field.getType();   
         Class real = getConstraint(type);
         
         return Constraint.getConstraint(real, modifiers);
      }
      return NONE;
   }
   
   public Constraint toConstraint(Method method, int modifiers) {
      if(method != null) {
         Class type = method.getReturnType();    
         Class real = getConstraint(type);
         
         return Constraint.getConstraint(real);        
      }
      return NONE;
   }
   
   private Class getConstraint(Class type) {
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
