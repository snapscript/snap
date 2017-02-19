/*
 * SuperConstructor.java December 2016
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

import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;

import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Type;
import org.snapscript.core.define.Initializer;
import org.snapscript.core.define.SuperExtractor;
import org.snapscript.parse.StringToken;
import org.snapscript.tree.ArgumentList;
import org.snapscript.tree.literal.TextLiteral;

public class SuperConstructor implements TypePart {
   
   private final SuperExtractor extractor;
   private final ArgumentList arguments;
   
   public SuperConstructor() {
      this(null, null);
   }
   
   public SuperConstructor(StringToken token) {
      this(token, null);
   }
   
   public SuperConstructor(ArgumentList arguments) {
      this(null, arguments);
   }
   
   public SuperConstructor(StringToken token, ArgumentList arguments) {
      this.extractor = new SuperExtractor();
      this.arguments = arguments;
   }
   
   @Override
   public Initializer define(Initializer initializer, Type type) throws Exception {
      return null;
   }

   @Override
   public Initializer compile(Initializer initializer, Type type) throws Exception {
      Type base = extractor.extractor(type);
      
      if(base == null) {
         throw new InternalStateException("No super type for '" + type + "'");
      }     
      return assemble(initializer, base);
   }
   
   protected Initializer assemble(Initializer statements, Type type) throws Exception {
      StringToken name = new StringToken(TYPE_CONSTRUCTOR);
      Evaluation literal = new TextLiteral(name);
      Evaluation evaluation = new SuperInvocation(literal, arguments, type);
      
      return new SuperInitializer(evaluation, type);
   }
}
