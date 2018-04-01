package org.snapscript.core.convert;

import static org.snapscript.core.convert.Score.EXACT;
import static org.snapscript.core.convert.Score.SIMILAR;

import org.snapscript.core.type.Type;

public class NullConverter extends ConstraintConverter { 

   @Override
   public Score score(Type type) throws Exception {
      if(type != null) {
         return SIMILAR; 
      }
      return EXACT;
   }
   
   @Override
   public Score score(Object value) throws Exception {
      if(value != null) {
         return SIMILAR; 
      }
      return EXACT; 
   }
   
   @Override
   public Object convert(Object object) {
      return object;
   }
}