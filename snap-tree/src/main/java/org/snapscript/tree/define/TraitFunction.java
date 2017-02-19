/*
 * TraitFunction.java December 2016
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

import org.snapscript.core.Evaluation;
import org.snapscript.core.ModifierType;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.define.Initializer;
import org.snapscript.tree.ModifierList;
import org.snapscript.tree.annotation.AnnotationList;
import org.snapscript.tree.constraint.Constraint;
import org.snapscript.tree.function.ParameterList;

public class TraitFunction extends MemberFunction {
 
   public TraitFunction(AnnotationList annotations, ModifierList list, Evaluation identifier, ParameterList parameters){
      super(annotations, list, identifier, parameters);
   }
   
   public TraitFunction(AnnotationList annotations, ModifierList list, Evaluation identifier, ParameterList parameters, Constraint constraint){
      super(annotations, list, identifier, parameters, constraint);
   }
   
   public TraitFunction(AnnotationList annotations, ModifierList list, Evaluation identifier, ParameterList parameters, Statement body){  
      super(annotations, list, identifier, parameters, body);
   }
   
   public TraitFunction(AnnotationList annotations, ModifierList list, Evaluation identifier, ParameterList parameters, Constraint constraint, Statement body){  
      super(annotations, list, identifier, parameters, constraint, body);
   } 
   
   @Override
   protected Initializer assemble(Initializer initializer, Type type, int mask) throws Exception {
      if(body == null) {
         return super.assemble(initializer, type, ModifierType.ABSTRACT.mask);
      }  
      return super.assemble(initializer, type, 0);
   }
}
