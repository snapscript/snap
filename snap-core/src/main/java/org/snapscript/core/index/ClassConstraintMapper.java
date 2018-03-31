package org.snapscript.core.index;

import static org.snapscript.core.constraint.Constraint.CONSTANT;
import static org.snapscript.core.constraint.Constraint.NONE;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.snapscript.core.Any;
import org.snapscript.core.Bug;
import org.snapscript.core.ModifierType;
import org.snapscript.core.PrimitivePromoter;
import org.snapscript.core.constraint.Constraint;

@Bug("crap")
public class ClassConstraintMapper {
   
   private final PrimitivePromoter promoter;
   
   public ClassConstraintMapper() {
      this.promoter = new PrimitivePromoter();
   }
   
   public Constraint toConstraint(Field field, int modifiers) {
      if(field != null) {
         Class type = field.getType();   
         Class real = promoter.promote(type);
         
         if(ModifierType.isConstant(modifiers)) {
            if(!isConstraint(real)) {
               return Constraint.getFinal(real);
            }
            return CONSTANT;
         }
         if(!isConstraint(real)) {
            return Constraint.getVariable(real);
         }
      }
      return NONE;
   }
   
   public Constraint toConstraint(Method method, int modifiers) {
      if(method != null) {
         Class type = method.getReturnType();    
         Class real = promoter.promote(type);
         
         if(isConstraint(real)) {
            return Constraint.getVariable(real);
         }         
      }
      return NONE;
   }
   
   private boolean isConstraint(Class type) {
      if(type == Object.class) {
         return false;
      }
      if(type == Any.class){
         return false;
      }
      if(type == void.class){
         return false;
      }
      return true;
   }
}
