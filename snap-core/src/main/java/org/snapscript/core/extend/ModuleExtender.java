/*
 * ModuleExtender.java December 2016
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

package org.snapscript.core.extend;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.TypeLoader;
import org.snapscript.core.function.Function;

public class ModuleExtender {
   
   private final List<Function> functions;
   private final Context context;
   
   public ModuleExtender(Context context) {
      this.functions = new ArrayList<Function>();
      this.context = context;
   }
   
   public synchronized void extend(Module module){
      List<Function> available = module.getFunctions();
      TypeLoader loader = context.getLoader();
      
      if(functions.isEmpty()) {
         FunctionExtractor extractor = new FunctionExtractor(loader);
         ScopeExtension extension = new ScopeExtension(context);
         
         try {
            List<Function> list = extractor.extract(module, Scope.class, extension);
            
            for(Function function : list) {
               functions.add(function);
            }
         } catch(Exception e) {
            throw new InternalStateException("Could not export runtime", e);
         }
      }
      available.addAll(functions);
   }

}
