/*
 * ClassBuilder.java December 2016
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

import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.tree.annotation.AnnotationList;

public class ClassBuilder extends Statement {   
   
   private final ClassConstantInitializer builder;
   private final AnnotationList annotations;
   private final TypeHierarchy hierarchy;
   private final TypeName name;
   
   public ClassBuilder(AnnotationList annotations, TypeName name, TypeHierarchy hierarchy) {
      this.builder = new ClassConstantInitializer();
      this.annotations = annotations;
      this.hierarchy = hierarchy;
      this.name = name;
   }
   
   @Override
   public Result define(Scope outer) throws Exception {
      Module module = outer.getModule();
      String alias = name.getName(outer);
      Type type = module.addType(alias);
      
      return ResultType.getNormal(type);
   }
   
   @Override
   public Result compile(Scope outer) throws Exception {
      Module module = outer.getModule();
      String alias = name.getName(outer);
      Type type = module.getType(alias);
      Scope scope = type.getScope();
      
      annotations.apply(scope, type);
      builder.declare(scope, type);
      hierarchy.update(scope, type); // this may throw exception if missing type
      
      return ResultType.getNormal(type);
   }
}
