package org.snapscript.core.convert;

import static org.snapscript.core.convert.Score.SIMILAR;

import org.snapscript.core.Type;

public class NullConverter extends ConstraintConverter { 

   @Override
   public Score score(Type type) throws Exception {
      return SIMILAR; 
   }
   
   @Override
   public Score score(Object value) throws Exception {
      return SIMILAR; 
   }
   
   @Override
   public Object convert(Object object) {
      return object;
   }
}