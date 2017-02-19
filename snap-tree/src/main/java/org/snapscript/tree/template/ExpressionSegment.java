/*
 * ExpressionSegment.java December 2016
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

package org.snapscript.tree.template;

import java.io.Writer;

import org.snapscript.core.convert.StringBuilder;
import org.snapscript.core.Context;
import org.snapscript.core.ExpressionEvaluator;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;

public class ExpressionSegment implements Segment {
   
   private String expression;
   private char[] source;
   private int off;
   private int length;
   
   public ExpressionSegment(char[] source, int off, int length) {
      this.expression = new String(source, off + 2, length - 3);
      this.source = source;
      this.length = length;
      this.off = off;         
   }
   
   @Override
   public void process(Scope scope, Writer writer) throws Exception {
      Module module = scope.getModule();
      String name = module.getName();
      Context context = module.getContext();
      ExpressionEvaluator evaluator = context.getEvaluator();
      Object value = evaluator.evaluate(scope, expression, name);
      
      if(value == null) {
         writer.write(source, off, length);
      } else {
         String text = StringBuilder.create(scope, value);
         
         writer.append(text);            
      }
   }   
   
   @Override
   public String toString() {
      return expression;
   }
}
