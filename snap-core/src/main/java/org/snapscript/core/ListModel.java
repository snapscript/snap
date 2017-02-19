/*
 * ListModel.java December 2016
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

import java.util.List;

import org.snapscript.core.Model;

public class ListModel implements Model {
   
   private final List<Model> models;
   
   public ListModel(List<Model> models) {
      this.models = models;
   }

   @Override
   public Object getAttribute(String name) {
      for(Model model : models) {
         Object value = model.getAttribute(name);
         
         if(value != null) {
            return value;
         }
      }
      return null;
   }

}
