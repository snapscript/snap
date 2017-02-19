/*
 * ProxyArgumentExtractor.java December 2016
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

package org.snapscript.core.convert;

public class ProxyArgumentExtractor {

   private final ProxyWrapper wrapper;
   private final Object[] empty;
   
   public ProxyArgumentExtractor(ProxyWrapper wrapper) {
      this.empty = new Object[]{};
      this.wrapper = wrapper;
   }
   
   public Object[] extract(Object[] arguments) {
      if(arguments != null) {
         Object[] convert = new Object[arguments.length];
         
         for(int i = 0; i < arguments.length; i++) {
            Object argument = arguments[i];
            Object value = wrapper.fromProxy(argument);
            
            convert[i] = value;
         }
         return convert;
      }
      return empty;
   }
}
