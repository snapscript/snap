/*
 * EnumInitializer.java December 2016
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

import static org.snapscript.core.Reserved.ENUM_NAME;
import static org.snapscript.core.Reserved.ENUM_ORDINAL;
import static org.snapscript.core.Reserved.ENUM_VALUES;

import java.util.List;
import java.util.concurrent.Callable;

import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.convert.ProxyWrapper;
import org.snapscript.core.define.Initializer;
import org.snapscript.tree.ArgumentList;
import org.snapscript.tree.NameExtractor;

public class EnumInitializer extends Initializer {
   
   private final EnumConstantInitializer initializer;
   private final EnumConstructorBinder binder;
   private final NameExtractor extractor;
   private final int index;
   
   public EnumInitializer(EnumKey key, ArgumentList arguments, int index) {
      this.initializer = new EnumConstantInitializer();
      this.binder = new EnumConstructorBinder(arguments);
      this.extractor = new NameExtractor(key);
      this.index = index;
   }
   
   @Override
   public Result execute(Scope scope, Type type) throws Exception {
      String name = extractor.extract(scope);
      State state = scope.getState();

      if(type == null) {
         throw new InternalStateException("No type found for enum " + name); // class not found
      }
      Callable<Result> call = binder.bind(scope, type);
      Module module = scope.getModule();
      Context context = module.getContext();
      ProxyWrapper wrapper = context.getWrapper();
      
      if(call == null){
         throw new InternalStateException("No constructor for enum '" + name + "' in '" + type+ "'");
      }
      Result result = call.call();
      Scope instance = result.getValue();
      Value value = state.get(ENUM_VALUES);
      List values = value.getValue();
      Object object = wrapper.toProxy(instance);
      
      initializer.declareConstant(scope, name, type, type, instance);
      initializer.declareConstant(instance, ENUM_NAME, type, name); // might declare name as property many times
      initializer.declareConstant(instance, ENUM_ORDINAL, type, index);
      values.add(object);
      
      return ResultType.getNormal(instance);
   }

}
