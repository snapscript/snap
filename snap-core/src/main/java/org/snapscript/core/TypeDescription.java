/*
 * TypeDescription.java December 2016
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

public class TypeDescription {

   private final Type type;
   
   public TypeDescription(Type type) {
      this.type = type;
   }
   
   public String getDescription(){
      StringBuilder builder = new StringBuilder();
      
      if(type != null) {
         String name = type.getName();
         Module module = type.getModule();
         
         builder.append(module);
         
         if(name != null) {
            builder.append(".");
            builder.append(name);
         }
      }
      return builder.toString();
   }
   
   public String toString() {
      return getDescription();
   }
}
