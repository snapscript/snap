/*
 * FutureDefinition.java December 2016
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

package org.snapscript.core.link;

import java.util.concurrent.FutureTask;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Path;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;

public class FutureDefinition implements PackageDefinition {
   
   private final FutureTask<PackageDefinition> result;
   private final Path path;
   
   public FutureDefinition(FutureTask<PackageDefinition> result, Path path) {
      this.result = result;
      this.path = path;
   }

   @Override
   public Statement compile(Scope scope, Path from) throws Exception {
      PackageDefinition definition = result.get();
      
      if(definition == null) {
         throw new InternalStateException("Could not compile '" + path + "'");
      }
      return definition.compile(scope, from);
   }     
}
