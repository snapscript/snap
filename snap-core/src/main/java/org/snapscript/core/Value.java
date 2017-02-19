/*
 * Value.java December 2016
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

public abstract class Value {
   
   public Boolean getBoolean() {
      Object value = getValue();

      if (value != null) {
         return (Boolean) value;// optimistic!!
      }
      return null;
   }

   public Number getNumber() {
      Object value = getValue();

      if (value != null) {
         return (Number) value; // optimistic!!
      }
      return null;
   }

   public Double getDouble() {
      Number number = getNumber();

      if (number != null) {
         return number.doubleValue();
      }
      return null;
   }

   public Long getLong() {
      Number number = getNumber();

      if (number != null) {
         return number.longValue();
      }
      return null;
   }

   public Integer getInteger() {
      Number number = getNumber();

      if (number != null) {
         return number.intValue();
      }
      return null;
   }

   public Float getFloat() {
      Number number = getNumber();

      if (number != null) {
         return number.floatValue();
      }
      return null;
   }

   public String getString() {
      Object value = getValue();

      if (value != null) {
         return value.toString();
      }
      return null;
   }   
   
   public Class getType() {
      Object value = getValue();
      
      if(value != null) {
         return value.getClass();         
      }
      return null;
   }     
   
   public Type getConstraint(){
      return null; 
   }   
   
   public boolean isProperty() {
      return false;
   }
   
   public int getModifiers(){
      return -1;
   }

   public abstract <T> T getValue();
   public abstract void setValue(Object value);
}
