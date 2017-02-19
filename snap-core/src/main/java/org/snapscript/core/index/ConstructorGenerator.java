/*
 * ConstructorGenerator.java December 2016
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

import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;

import java.lang.reflect.Constructor;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Type;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationFunction;
import org.snapscript.core.function.Signature;

public class ConstructorGenerator {

   private final SignatureGenerator generator;
   
   public ConstructorGenerator(TypeIndexer indexer) {
      this.generator = new SignatureGenerator(indexer);
   }
   
   public Function generate(Type type, Constructor constructor, Class[] types, int modifiers) {
      Signature signature = generator.generate(type, constructor);
      
      try {
         Invocation invocation = new ConstructorInvocation(constructor);
         
         if(!constructor.isAccessible()) {
            constructor.setAccessible(true);
         }
         return new InvocationFunction(signature, invocation, type, type, TYPE_CONSTRUCTOR, modifiers);
      } catch(Exception e) {
         throw new InternalStateException("Could not create function for " + constructor, e);
      }
   } 
}
