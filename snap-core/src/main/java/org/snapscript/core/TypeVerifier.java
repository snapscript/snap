/*
 * TypeVerifier.java December 2016
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

package org.snapscript.core;

import static org.snapscript.core.convert.Score.INVALID;

import org.snapscript.core.convert.Score;

public class TypeVerifier {
   
   private final TypeCastChecker checker;
   private final TypeLoader loader;
   
   public TypeVerifier(TypeLoader loader, TypeCastChecker checker) {
      this.checker = checker;
      this.loader = loader;
   }

   public boolean isSame(Type type, Class require) throws Exception {
      Class actual = type.getType();
      
      if(actual == require) {
         return true;
      }
      return false;
   }
   
   public boolean isLike(Type type, Class require) throws Exception {
      Type actual = loader.loadType(require);
      Score score = checker.cast(type, actual);
      
      return score.compareTo(INVALID) > 0;
   }
   
   public boolean isArray(Type type) throws Exception {
      Type entry = type.getEntry();
      
      if(entry != null) {
         return true;
      }
      return false;
   }
}
