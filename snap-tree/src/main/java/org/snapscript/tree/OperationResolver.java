/*
 * OperationResolver.java December 2016
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

package org.snapscript.tree;

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;
import org.snapscript.core.Context;
import org.snapscript.core.Type;
import org.snapscript.core.TypeLoader;

public class OperationResolver {
   
   private final Cache<String, Operation> registry;
   private final Context context;

   public OperationResolver(Context context) {
      this.registry = new CopyOnWriteCache<String, Operation>();
      this.context = context;
   }

   public Operation resolve(String name) throws Exception {
      Operation current = registry.fetch(name);
      
      if(current == null) {
         Instruction[] list = Instruction.values();       
         int size = registry.size();
         
         if(size < list.length) { // have they all been done?
            for(Instruction instruction :list){
               Operation operation = create(instruction);
               String grammar = instruction.getName();
               
               registry.cache(grammar, operation);
            }  
         } 
         return registry.fetch(name);
      }
      return current;
   }
   
   private Operation create(Instruction instruction) throws Exception{
      TypeLoader loader = context.getLoader();
      Class value = instruction.getType();
      Type type = loader.loadType(value);
      
      return new Operation(instruction, type);
   }
}
