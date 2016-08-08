package org.snapscript.core.convert;

import static org.snapscript.core.convert.Score.EXACT;
import static org.snapscript.core.convert.Score.INVALID;

import org.snapscript.core.function.ArgumentConverter;

public class FixedArgumentConverter implements ArgumentConverter { 

   private final ConstraintConverter[] converters;

   public FixedArgumentConverter(ConstraintConverter[] converters) {
      this.converters = converters;
   }
   
   @Override
   public Score score(Object... list) throws Exception {
      if(list.length != converters.length) {
         return INVALID;
      }
      if(list.length > 0) {
         Score total = INVALID; 
      
         for(int i = 0; i < list.length; i++){
            ConstraintConverter converter = converters[i];
            Object value = list[i];
            Score score = converter.score(value);
            
            if(score.compareTo(INVALID) == 0) {
               return INVALID;
            }
            total = Score.sum(total, score);
            
         }
         return total;
      }
      return EXACT;
   }
   
   @Override
   public Object[] convert(Object... list) throws Exception {
      if(list.length > 0) {
         Object[] result = new Object[list.length];
      
         for(int i = 0; i < list.length; i++){
            ConstraintConverter converter = converters[i];
            Object value = list[i];
            
            result[i] = converter.convert(value);
         }
         return result;
      }
      return list;
   }
}
