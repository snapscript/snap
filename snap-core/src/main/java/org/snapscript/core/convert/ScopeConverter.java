package org.snapscript.core.convert;

import static org.snapscript.core.convert.Score.EXACT;
import static org.snapscript.core.convert.Score.INVALID;
import static org.snapscript.core.convert.Score.POSSIBLE;

import org.snapscript.core.Scope;
import org.snapscript.core.Type;

public class ScopeConverter extends ConstraintConverter {
   
   @Override
   public Score score(Type actual) throws Exception {
      if(actual != null) {
         Class real = actual.getType();
         
         if(Scope.class.isAssignableFrom(real)) { // what is this doing
            return EXACT;
         }
         return INVALID;
      }
      return POSSIBLE;
   }
   
   @Override
   public Score score(Object value) throws Exception {
      if(value != null) {
         if(Scope.class.isInstance(value)) {
            return EXACT;
         }
         return INVALID;
      }
      return POSSIBLE;
   }
   
   @Override
   public Object convert(Object object) {
      return object;
   }
}