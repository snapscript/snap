/*
 * ValueKey.java December 2016
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

public class ValueKey {

   private final String name;
   private final Type type;
   
   public ValueKey(String name, Type type) {
      this.name = name;
      this.type = type;
   }
   
   @Override
   public boolean equals(Object key) {
      if(key instanceof ValueKey) {
         return equals((ValueKey)key);
      }
      return false;
   }
   
   public boolean equals(ValueKey key) {
      if(key.type != type) {
         return false;
      }
      return key.name.equals(name);
   }
   
   @Override
   public int hashCode() {
      int hash = name.hashCode();
      
      if(type != null) {
         return 31 * hash+type.hashCode();
      }
      return hash;
   }
   
   @Override
   public String toString() {
      return name;
   }
}
