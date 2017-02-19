/*
 * VariableSegment.java December 2016
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
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Value;

public class VariableSegment implements Segment {
   
   private String variable;
   private char[] source;
   private int off;
   private int length;
   
   public VariableSegment(char[] source, int off, int length) {
      this.variable = new String(source, off + 2, length - 3);
      this.source = source;
      this.length = length;
      this.off = off;         
   }
   
   @Override
   public void process(Scope scope, Writer writer) throws Exception {
      State state = scope.getState();
      Value value = state.get(variable);
      
      if(value == null) {
         writer.write(source, off, length);
      } else {
         Object token = value.getValue();
         String text = StringBuilder.create(scope, token);
         
         writer.append(text);            
      }
   }   
   
   @Override
   public String toString() {
      return variable;
   }
}
