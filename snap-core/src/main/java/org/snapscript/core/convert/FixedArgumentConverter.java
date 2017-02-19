/*
 * FixedArgumentConverter.java December 2016
 *
 * Copyright (C) 2016, Niall Gallagher <niallg@users.sf.net>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
 * implied. See the License for the specific language governing 
 * permissions and limitations under the License.
 */

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
         for(int i = 0; i < list.length; i++){
            ConstraintConverter converter = converters[i];
            Object value = list[i];
            
            list[i] = converter.convert(value);
         }
         return list;
      }
      return list;
   }
}
