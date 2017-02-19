/*
 * TextTemplate.java December 2016
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

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.parse.StringToken;

public class TextTemplate implements Evaluation {

   private volatile List<Segment> tokens;
   private volatile StringToken template;
   
   public TextTemplate(StringToken template) {
      this.tokens = new ArrayList<Segment>();
      this.template = template;
   }

   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      String text = template.getValue();
      
      if(text == null) {
         throw new InternalStateException("Text value was null");
      }
      String result = interpolate(scope, text);
   
      return ValueType.getTransient(result);
   }
   
   private String interpolate(Scope scope, String text) throws Exception {
      StringWriter writer = new StringWriter();
            
      if(tokens.isEmpty()) {
         SegmentIterator iterator = new SegmentIterator(text);
         List<Segment> list = new ArrayList<Segment>();
         
         while(iterator.hasNext()) {
            Segment token = iterator.next();
            
            if(token != null) {
               list.add(token);  
            }
         }
         tokens = list; // atomic swap
      }
      for(Segment token : tokens) {
         token.process(scope, writer);
      }
      return writer.toString();
   }
}


