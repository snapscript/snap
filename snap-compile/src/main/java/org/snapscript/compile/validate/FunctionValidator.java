/*
 * FunctionValidator.java December 2016
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

package org.snapscript.compile.validate;

import static org.snapscript.core.convert.Score.INVALID;

import java.util.List;
import java.util.Set;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.ModifierType;
import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.convert.ConstraintMatcher;
import org.snapscript.core.convert.FunctionComparator;
import org.snapscript.core.convert.Score;
import org.snapscript.core.function.Function;
import org.snapscript.tree.ModifierValidator;

public class FunctionValidator {
   
   private final FunctionComparator comparator;
   private final ModifierValidator validator;
   private final TypeExtractor extractor;
   
   public FunctionValidator(ConstraintMatcher matcher, TypeExtractor extractor) {
      this.comparator = new FunctionComparator(matcher);
      this.validator = new ModifierValidator();
      this.extractor = extractor;
   }
   
   public void validate(Function function) throws Exception {
      Type type = function.getType();
      
      if(type == null) {
         throw new InternalStateException("Function '" + function + "' does not have a type");
      }
      validateModifiers(function);
   }
   
   private void validateModifiers(Function function) throws Exception {
      Type actual = function.getType();
      int modifiers = function.getModifiers();
      
      if(ModifierType.isOverride(modifiers)) {
         Set<Type> types = extractor.getTypes(actual);
         int matches = 0;
         
         for(Type type : types) {
            if(type != actual) {
               List<Function> functions = type.getFunctions();
               Score score = comparator.compare(function, functions);
               
               if(score.compareTo(INVALID) != 0) {
                  matches++;
                  break;
               }
            }
         }
         if(matches == 0) {
            throw new InternalStateException("Function '" + function + "' is not an override");
         }
      }
      validator.validate(actual, function, modifiers);
   }
   
}
