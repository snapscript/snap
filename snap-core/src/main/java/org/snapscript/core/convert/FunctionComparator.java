/*
 * FunctionComparator.java December 2016
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
import static org.snapscript.core.convert.Score.POSSIBLE;

import java.util.List;

import org.snapscript.core.Type;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.function.Signature;

public class FunctionComparator {
   
   private final ConstraintMatcher matcher;
   
   public FunctionComparator(ConstraintMatcher matcher) {
      this.matcher = matcher;
   }
   
   public Score compare(Function actual, List<Function> require) throws Exception{
      String name = actual.getName();
      
      for(Function function : require) {
         String match = function.getName();
         
         if(name.equals(match)) {
            Score compare = compare(actual, function);
            
            if(compare != INVALID) {
               return compare;
            }
         }
      }
      return INVALID;
   }

   public Score compare(Function actual, Function require) throws Exception{
      Signature actualSignature = actual.getSignature();
      Signature requireSignature = require.getSignature();
      List<Parameter> actualParameters = actualSignature.getParameters();
      List<Parameter> requireParameters = requireSignature.getParameters();
      int actualSize = actualParameters.size();
      int requireSize = requireParameters.size();
      boolean actualVariable = actualSignature.isVariable();
      
      if(actualSize == requireSize) {
         Score score = compare(actualParameters, requireParameters);
         
         if(score.compareTo(INVALID) > 0) {
            return score;
         }
      }
      if(actualVariable && actualSize <= requireSize) {
         Score score = compare(actualParameters, requireParameters); // compare(a...) == compare(a, b)
         
         if(score.compareTo(INVALID) > 0) {
            return score;
         }
      }
      return INVALID;
   }
   
   private Score compare(List<Parameter> actual, List<Parameter> require) throws Exception{
      int requireSize = require.size();
      
      if(requireSize > 0) {
         Score total = INVALID;
         
         for(int i = 0, j = 0; i < requireSize; i++) {
            Parameter actualParameter = actual.get(j);
            Parameter requireParameter = require.get(i);
            Score score = compare(actualParameter, requireParameter);
            
            if(score.compareTo(INVALID) <= 0) { // must check for numbers
               return INVALID;
            }
            total = Score.sum(total, score); // sum for better match
            
            if(!actualParameter.isVariable()) { // if variable stick
               j++;
            }
         }
         return total;
      }
      return EXACT;
   }
   
   private Score compare(Parameter actual, Parameter require) throws Exception{
      Type actualType  = actual.getType();
      Type constraintType = require.getType();
      ConstraintConverter converter = matcher.match(constraintType);
      Score score = converter.score(actualType);
      
      if(actual.isVariable()) {
         if(score.compareTo(INVALID) <= 0) {
            return INVALID;
         }
         return POSSIBLE;
      }
      return score;
   }
}
