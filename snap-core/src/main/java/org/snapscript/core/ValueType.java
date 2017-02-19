/*
 * ValueType.java December 2016
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

public enum ValueType {
   CONSTANT,
   REFERENCE,
   TRANSIENT,
   BLANK,
   PROPERTY;
   
   public static Value getConstant(Object value) {
      return new Constant(value);
   }
   
   public static Value getConstant(Object value, Type type) {
      return new Constant(value, type);
   }
   
   public static Value getReference(Object value) {
      return new Reference(value);
   }
   
   public static Value getReference(Object value, Type type) {
      return new Reference(value, type);
   }
   
   public static Value getProperty(Object value, Type type, int modifiers) {
      return new Reference(value, type, modifiers);
   }
   
   public static Value getBlank(Object value, Type type, int modifiers) {
      return new Blank(value, type, modifiers);
   }
   
   public static Value getTransient(Object value) {
      return new Transient(value);
   }
   
   public static Value getTransient(Object value, Type type) {
      return new Transient(value, type);
   }
}
