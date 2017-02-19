/*
 * ThisConstructor.java December 2016
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
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.Identity;
import org.snapscript.core.define.Initializer;
import org.snapscript.parse.StringToken;
import org.snapscript.tree.ArgumentList;
import org.snapscript.tree.construct.CreateObject;

public class ThisConstructor implements TypePart {
   
   private final ArgumentList arguments;
   
   public ThisConstructor(StringToken token) {
      this(token, null);
   }
   
   public ThisConstructor(ArgumentList arguments) {
      this(null, arguments);
   }
   
   public ThisConstructor(StringToken token, ArgumentList arguments) {
      this.arguments = arguments;
   }

   @Override
   public Initializer define(Initializer initializer, Type type) throws Exception {
      return null;
   }
   
   @Override
   public Initializer compile(Initializer initializer, Type type) throws Exception {  
      Statement statement = new StaticBody(initializer, type);
      Evaluation reference = new Identity(type);
      CreateObject evaluation = new CreateObject(reference, arguments);
      
      return new ThisInitializer(statement, evaluation);
   }
}