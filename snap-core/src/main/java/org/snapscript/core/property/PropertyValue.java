/*
 * PropertyValue.java December 2016
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

package org.snapscript.core.property;

import org.snapscript.core.Value;

public class PropertyValue extends Value {

   private final Property property;
   private final Object object;   
   private final String name;

   public PropertyValue(Property property, Object object, String name) {
      this.property = property;
      this.object = object;
      this.name = name;
   }
   
   @Override
   public boolean isProperty() {
      return true;
   }
   
   @Override
   public int getModifiers() {
      return property.getModifiers();
   }

   public String getName(){
      return name;
   }
   
   @Override
   public <T> T getValue() {
      return (T)property.getValue(object);
   }

   @Override
   public void setValue(Object value) {
      property.setValue(object, value);
   }
   
   @Override
   public String toString() {
      return String.valueOf(object);
   }
}
