/*
 * CalculationList.java December 2016
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

package org.snapscript.tree.operation;

import org.snapscript.core.Compilation;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Module;
import org.snapscript.core.Path;

public class CalculationList implements Compilation { 
   
   private CalculationPart[] parts; 

   public CalculationList(CalculationPart... parts) {
      this.parts = parts;
   }
   
   @Override
   public Evaluation compile(Module module, Path path, int line) throws Exception {
      Calculator calculator = new Calculator();
      
      for(CalculationPart part : parts) {
         calculator.update(part);
      }
      return calculator.create();
   }
}