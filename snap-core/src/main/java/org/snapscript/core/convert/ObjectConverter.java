/*
 * ObjectConverter.java December 2016
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

import org.snapscript.core.Type;
import org.snapscript.core.TypeCastChecker;
import org.snapscript.core.TypeExtractor;

public class ObjectConverter extends ConstraintConverter {
   
   private final TypeCastChecker checker;
   private final TypeExtractor extractor;
   private final ProxyWrapper wrapper;
   private final Type constraint;
   
   public ObjectConverter(TypeExtractor extractor, TypeCastChecker checker, ProxyWrapper wrapper, Type constraint) {
      this.constraint = constraint;
      this.extractor = extractor;
      this.wrapper = wrapper;
      this.checker = checker;
   }
   
   @Override
   public Score score(Type actual) throws Exception {
      if(actual != null) {
         Class real = actual.getType();
         Class require = constraint.getType();
         
         if(require == real) {
            return EXACT;
         }
         return checker.cast(actual, constraint);
      }
      return EXACT;
   }

   @Override
   public Score score(Object value) throws Exception { // argument type
      Type match = extractor.getType(value);
      
      if(match != null) {
         if(match.equals(constraint)) {
            return EXACT;
         }
         return checker.cast(match, constraint, value);
      }
      return EXACT;
   }
   
   @Override
   public Object convert(Object object) {
      Class require = constraint.getType();
      
      if(require != null) {
         return wrapper.toProxy(object, require);
      }
      return object;
   }
}