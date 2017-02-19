/*
 * MapEntry.java December 2016
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

package org.snapscript.tree.construct;

import java.util.Map.Entry;

import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;

public class MapEntry {
   
   private final Evaluation value;
   private final Evaluation key;
   
   public MapEntry(Evaluation key, Evaluation value) {
      this.value = value;
      this.key = key;
   }
   
   public Entry create(Scope scope) throws Exception{
      Value valueResult = value.evaluate(scope, null);
      Value keyResult = key.evaluate(scope, null);
      Object valueObject = valueResult.getValue();
      Object keyObject = keyResult.getValue();
      
      return new Pair(keyObject, valueObject);
   }
   
   private class Pair implements Entry {
      
      private final Object value;
      private final Object key;
      
      public Pair(Object key, Object value) {
         this.value = value;
         this.key = key;
      }

      @Override
      public Object getKey() {
         return key;
      }

      @Override
      public Object getValue() {
         return value;
      }

      @Override
      public Object setValue(Object value) {
         throw new InternalStateException("Modification of constant entry '" + key + "'");
      }
      
   }
}