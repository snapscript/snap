/*
 * MapValue.java December 2016
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

import java.util.Map;

import org.snapscript.core.Value;
import org.snapscript.core.convert.ProxyWrapper;

public class MapValue extends Value {
   
   private final ProxyWrapper wrapper;
   private final Object key;
   private final Map map;
   
   public MapValue(ProxyWrapper wrapper, Map map, Object key) {
      this.wrapper = wrapper;
      this.key = key;
      this.map = map;
   }
   
   @Override
   public Class getType() {
      return Object.class;
   }
   
   @Override
   public Object getValue(){
      Object value = map.get(key);
      
      if(value != null) {
         return wrapper.fromProxy(value);
      }
      return value;
   }
   
   @Override
   public void setValue(Object value){
      Object proxy = wrapper.toProxy(value);
      
      if(value != null) {
         map.put(key, proxy);
      } else {
         map.remove(key);
      }
   }       
   
   @Override
   public String toString() {
      return String.valueOf(key);
   }
}