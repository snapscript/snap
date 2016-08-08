package org.snapscript.core.convert;

import static org.snapscript.core.convert.Score.EXACT;
import static org.snapscript.core.convert.Score.INVALID;

import org.snapscript.core.function.ArgumentConverter;

public class VariableArgumentConverter implements ArgumentConverter { 
   
   private final ConstraintConverter[] converters;

   public VariableArgumentConverter(ConstraintConverter[] converters) {
      this.converters = converters;
   }
   
   @Override
   public Score score(Object... list) throws Exception {
      if(list.length > 0) {
         int require = converters.length;
         int start = require - 1;
         int remaining = list.length - start;
         
         if(remaining < 0) {
            return INVALID;
         }
         Score total = INVALID;
         
         for(int i = 0; i < start; i++){
            ConstraintConverter converter = converters[i];
            Object value = list[i];
            Score score = converter.score(value);
            
            if(score.compareTo(INVALID) == 0) {
               return INVALID;
            }
            total = Score.sum(total, score);
         }
         if (remaining > 0) {
            for (int i = 0; i < remaining; i++) {
               ConstraintConverter converter = converters[require - 1];
               Object value = list[i + start];
               Score score = converter.score(value);
               
               if(score.compareTo(INVALID) == 0) {
                  return INVALID;
               }
               total = Score.sum(total, score);
            }
         }
         return total;
      }
      if(converters.length == 1) {
         return EXACT;
      }
      return INVALID;
      
   }

   @Override
   public Object[] convert(Object... list) throws Exception {
      if(list.length > 0) {
         int require = converters.length;
         int start = require - 1;
         int remaining = list.length - start;
         
         for(int i = 0; i < start; i++){
            ConstraintConverter converter = converters[i];
            Object value = list[i];
            
            list[i] = converter.convert(value);
         }
         if (remaining > 0) {
            for (int i = 0; i < remaining; i++) {
               ConstraintConverter converter = converters[require - 1];
               Object value = list[i + start];
               
               list[i + start] = converter.convert(value);
            }
         }
      }
      return list;
   }
}
