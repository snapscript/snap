/*
 * CollectionConverter.java December 2016
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

package org.snapscript.tree.collection;

import java.util.List;
import java.util.Map;

import org.snapscript.core.InternalArgumentException;

public class CollectionConverter {

   private final ArrayBuilder builder;
   
   public CollectionConverter() {
      this.builder = new ArrayBuilder();
   }
   
   public boolean accept(Object value) throws Exception {
      if(value != null) {
         Class type = value.getClass();
         
         if(type.isArray()) {
            return true;
         }
         if(List.class.isAssignableFrom(type)) {
            return true;
         }
         if(Map.class.isAssignableFrom(type)) {
            return true;
         }
      }
      return false;
   }
   
   public Object convert(Object value) throws Exception {
      if(value != null) {
         Class type = value.getClass();
         
         if(type.isArray()) {
            return builder.convert(value);
         }
         if(List.class.isAssignableFrom(type)) {
            return value;
         }
         if(Map.class.isAssignableFrom(type)) {
            return value;
         }
         throw new InternalArgumentException("Illegal index of " + type);
      }
      return null;
   }
}

