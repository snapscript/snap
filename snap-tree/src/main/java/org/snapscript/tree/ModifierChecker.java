/*
 * ModifierChecker.java December 2016
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

import org.snapscript.core.ModifierType;

public class ModifierChecker extends ModifierData {

   private ModifierData list;
   private int modifiers;
   
   public ModifierChecker(ModifierData list) {
      this.modifiers = -1;
      this.list = list;
   }
   
   public int getModifiers() {
      if(modifiers == -1) {
         modifiers = list.getModifiers();
      }
      return modifiers;
   }
   
   public boolean isStatic() {
      if(modifiers == -1) {
         modifiers = list.getModifiers();
      }
      return ModifierType.isStatic(modifiers);
   }
   
   public boolean isConstant() {
      if(modifiers == -1) {
         modifiers = list.getModifiers();
      }
      return ModifierType.isConstant(modifiers);
   }
   
   public boolean isPublic() {
      if(modifiers == -1) {
         modifiers = list.getModifiers();
      }
      return ModifierType.isPublic(modifiers);
   }
   
   public boolean isPrivate() {
      if(modifiers == -1) {
         modifiers = list.getModifiers();
      }
      return ModifierType.isPrivate(modifiers);
   }
   
   public boolean isOverride() {
      if(modifiers == -1) {
         modifiers = list.getModifiers();
      }
      return ModifierType.isOverride(modifiers);
   }
}
