/*
 * SuperInvocation.java December 2016
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
import org.snapscript.core.Scope;
import org.snapscript.core.ScopeCombiner;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.tree.ArgumentList;
import org.snapscript.tree.NameExtractor;
import org.snapscript.tree.dispatch.InvocationBinder;
import org.snapscript.tree.dispatch.InvocationDispatcher;

public class SuperInvocation implements Evaluation {

   private final SuperInstanceBuilder builder;
   private final InvocationBinder dispatcher;
   private final NameExtractor extractor;
   private final ArgumentList arguments;
   
   public SuperInvocation(Evaluation function, ArgumentList arguments, Type type) {
      this.builder = new SuperInstanceBuilder(type);
      this.extractor = new NameExtractor(function);
      this.dispatcher = new InvocationBinder();
      this.arguments = arguments;
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      Scope instance = builder.create(scope, left);
      Type real = scope.getType();
      InvocationDispatcher handler = dispatcher.bind(instance, null);
      String name = extractor.extract(scope);     
      
      if(arguments != null) {
         Scope outer = real.getScope();
         Scope compound = ScopeCombiner.combine(scope, outer);
         Value array = arguments.create(compound, real); // arguments have no left hand side
         Object[] list = array.getValue();
         
         return handler.dispatch(name, list);
      }
      return handler.dispatch(name, real);
   }
   

}