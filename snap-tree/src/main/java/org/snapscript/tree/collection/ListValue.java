/*
 * ListValue.java December 2016
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

import org.snapscript.core.Value;
import org.snapscript.core.convert.ProxyWrapper;

public class ListValue extends Value {
   
   private final ProxyWrapper wrapper;
   private final Integer index;
   private final List list;
   
   public ListValue(ProxyWrapper wrapper, List list, Integer index) {
      this.wrapper = wrapper;
      this.index = index;
      this.list = list;
   }
   
   @Override
   public Class getType() {
      return Object.class;
   }
   
   @Override
   public Object getValue(){
      Object value = list.get(index);
      
      if(value != null) {
         return wrapper.fromProxy(value);
      }
      return value;
   }
   
   @Override
   public void setValue(Object value){
      Object proxy = wrapper.toProxy(value);
      int length = list.size();
      
      for(int i = length; i <= index; i++) {
         list.add(null);
      }
      list.set(index, proxy);
   }       
   
   @Override
   public String toString() {
      return String.valueOf(list);
   }
}