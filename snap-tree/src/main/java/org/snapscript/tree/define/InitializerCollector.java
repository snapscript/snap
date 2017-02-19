/*
 * InitializerCollector.java December 2016
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

package org.snapscript.tree.define;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.define.Initializer;

public class InitializerCollector extends Initializer {
   
   private final List<Initializer> list;
   
   public InitializerCollector(){
      this.list = new ArrayList<Initializer>();
   }

   public void update(Initializer initializer) throws Exception {
      if(initializer != null) {         
         list.add(initializer);
      }
   }
   
   @Override
   public Result compile(Scope scope, Type type) throws Exception {
      Result last = null;
      
      for(Initializer initializer : list) {
         Result result = initializer.compile(scope, type);
         
         if(!result.isNormal()){
            return result;
         }
         last = result;
      }
      if(last == null) {
         return ResultType.getNormal();
      }
      return last;
   } 

   @Override
   public Result execute(Scope scope, Type type) throws Exception {
      Result last = null;

      for(Initializer initializer : list) {
         Result result = initializer.execute(scope, type);
         
         if(!result.isNormal()){
            return result;
         }
         last = result;
      }
      if(last == null) {
         return ResultType.getNormal();
      }
      return last;
   }              
}
