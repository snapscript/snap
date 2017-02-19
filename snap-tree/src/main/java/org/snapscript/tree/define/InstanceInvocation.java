/*
 * InstanceInvocation.java December 2016
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

import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.convert.ConstraintConverter;
import org.snapscript.core.convert.ConstraintMatcher;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.Signature;
import org.snapscript.core.function.SignatureAligner;
import org.snapscript.tree.function.ParameterExtractor;

public class InstanceInvocation implements Invocation<Scope> {

   private final ParameterExtractor extractor;
   private final SignatureAligner aligner;
   private final ThisScopeBinder binder;
   private final Statement statement;
   private final Type constraint;
   private final String name;
   
   public InstanceInvocation(Signature signature, Statement statement, Type constraint, String name) {
      this.extractor = new ParameterExtractor(signature);
      this.aligner = new SignatureAligner(signature);
      this.binder = new ThisScopeBinder();
      this.constraint = constraint;
      this.statement = statement;
      this.name = name;
   }
   
   @Override
   public Result invoke(Scope scope, Scope instance, Object... list) throws Exception {
      if(statement == null) {
         throw new InternalStateException("Function '" + name + "' is abstract");
      }
      Object[] arguments = aligner.align(list); 
      Module module = scope.getModule();
      Context context = module.getContext();
      Scope outer = binder.bind(scope, instance);
      Scope inner = outer.getInner(); // create a writable scope
      
      if(arguments.length > 0) {
         extractor.extract(inner, arguments);
      }
      ConstraintMatcher matcher = context.getMatcher();
      ConstraintConverter converter = matcher.match(constraint);
      Result result = statement.execute(inner);
      Object value = result.getValue();
      
      if(value != null) {
         value = converter.convert(value);
      }
      return ResultType.getNormal(value);
   }

}
