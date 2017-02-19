/*
 * FunctionKey.java December 2016
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

package org.snapscript.core.bind;

import org.snapscript.core.Type;

public class FunctionKey {      

   private Type[] types;
   private String function;
   private int hash;
   
   public FunctionKey(String function, Type[] types) {
      this.function = function;
      this.types = types;
   }
   
   @Override
   public boolean equals(Object key) {
      if(key instanceof FunctionKey) {
         return equals((FunctionKey)key);
      }
      return false;
   }
   
   public boolean equals(FunctionKey key) {
      if(key.types.length != types.length) {
         return false;
      }
      for(int i = 0; i < types.length; i++) {
         if(types[i] != key.types[i]) {
            return false;
         }         
      }
      return key.function.equals(function);
   }
   
   @Override
   public int hashCode() {
      if(hash == 0) {
         int value = function.hashCode();
         
         for(Type type : types) {
            int order = 1;
            
            if(type != null) {
               order = type.getOrder();
            }
            value = value *31 + order;
         }
         hash = value;
      }
      return hash;
   }
}
