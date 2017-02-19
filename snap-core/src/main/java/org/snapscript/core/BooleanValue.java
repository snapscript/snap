/*
 * BooleanValue.java December 2016
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

public class BooleanValue extends Value {   
   
   public static final BooleanValue TRUE = new BooleanValue(true);
   public static final BooleanValue FALSE = new BooleanValue(false); 

   private final Boolean value;
   
   public BooleanValue(Boolean value) {
      this.value = value;
   }
   
   @Override
   public Class getType() {
      return Boolean.class;
   }     
   
   @Override
   public Boolean getValue(){
      return value;
   }
   
   @Override
   public void setValue(Object value){
      throw new InternalStateException("Illegal modification of value");
   }
   
   @Override
   public String toString() {
      return String.valueOf(value);
   }
}
