/*
 * FunctionGenerator.java December 2016
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

package org.snapscript.core.index;

import java.lang.reflect.Method;

import org.snapscript.core.Any;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Type;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationFunction;
import org.snapscript.core.function.Signature;

public class FunctionGenerator {
   
   private final SignatureGenerator generator;
   private final DefaultMethodChecker checker;
   private final TypeIndexer indexer;
   
   public FunctionGenerator(TypeIndexer indexer) {
      this.generator = new SignatureGenerator(indexer);
      this.checker = new DefaultMethodChecker();
      this.indexer = indexer;
   }

   public Function generate(Type type, Method method, Class[] types, String name, int modifiers) {
      Signature signature = generator.generate(type, method);
      Class real = method.getReturnType();
      
      try {
         Invocation invocation = null;
         
         if(checker.check(method)) {
            invocation = new DefaultMethodInvocation(method);
         } else {
            invocation = new MethodInvocation(method);
         }
         Type returns = indexer.loadType(real);
         
         if(!method.isAccessible()) {
            method.setAccessible(true);
         }
         if(real != void.class && real != Any.class && real != Object.class) {
            return new InvocationFunction(signature, invocation, type, returns, name, modifiers);
         }
         return new InvocationFunction(signature, invocation, type, null, name, modifiers);
      } catch(Exception e) {
         throw new InternalStateException("Could not create function for " + method, e);
      }
   }
}
