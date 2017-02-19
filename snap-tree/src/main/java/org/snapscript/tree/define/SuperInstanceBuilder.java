/*
 * SuperInstanceBuilder.java December 2016
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

import org.snapscript.core.Model;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.define.Instance;
import org.snapscript.core.define.SuperInstance;

public class SuperInstanceBuilder {
   
   private final Type type;
   
   public SuperInstanceBuilder(Type type) {
      this.type = type;
   }

   public Scope create(Scope scope, Object left) throws Exception {
      Type real = (Type)left;
      Instance instance = (Instance)scope;
      Instance outer = instance.getOuter();
      Module module = type.getModule();
      Model model = scope.getModel();

      return new SuperInstance(module, model, outer, real, type);
   }
}
