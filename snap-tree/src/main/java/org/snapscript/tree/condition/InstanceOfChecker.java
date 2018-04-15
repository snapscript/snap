package org.snapscript.tree.condition;

import java.util.Set;

import org.snapscript.core.Context;
import org.snapscript.core.PrimitivePromoter;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeExtractor;

public class InstanceOfChecker {

   private final PrimitivePromoter promoter;

   public InstanceOfChecker() {
      this.promoter = new PrimitivePromoter();
   }

   public boolean isInstanceOf(Scope scope, Object instance, Object constraint) {
      if (constraint != null && instance != null) {
         try {
            Module module = scope.getModule();
            Context context = module.getContext();
            TypeExtractor extractor = context.getExtractor();
            Type actual = extractor.getType(instance);
            Set<Type> types = extractor.getTypes(actual);
            Type require = (Type) constraint;

            if (!types.contains(require)) {
               Class actualType = actual.getType();
               Class requireType = require.getType();

               return isInstanceOf(scope, actualType, requireType);
            }
            return true;
         } catch (Exception e) {
            return false;
         }
      }
      return false;
   }

   private boolean isInstanceOf(Scope scope, Class instance, Class constraint) {
      if (constraint != null && instance != null) {
         try {
            if(instance.isArray() && constraint.isArray()) {
               Class instanceEntry = instance.getComponentType();
               Class constraintEntry = constraint.getComponentType();
               
               return isInstanceOf(scope, instanceEntry, constraintEntry);
            }
            Class instanceType = promoter.promote(instance);
            Class constraintType = promoter.promote(constraint);

            return instanceType == constraintType;
         } catch (Exception e) {
            return false;
         }
      }
      return false;
   }
}