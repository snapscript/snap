/*
 * ModifierData.java December 2016
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

import org.snapscript.core.InternalStateException;
import org.snapscript.core.ModifierType;

public class ModifierData {
   
   private final ModifierType[] types;
   
   public ModifierData(ModifierType... types) {
      this.types = types;
   }
   
   public int getModifiers(){
      int mask = 0;
      
      for(ModifierType type : types) {        
         if(type != null) {
            if((mask & type.mask) == type.mask) {
               throw new InternalStateException("Modifier '" + type + "' declared twice");
            }
            mask |= type.mask;
         }
      }
      return mask;
   }
}
