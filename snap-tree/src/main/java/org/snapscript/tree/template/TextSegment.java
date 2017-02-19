/*
 * TextSegment.java December 2016
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

import org.snapscript.core.Scope;

public class TextSegment implements Segment {
   
   private final String text;
   private final char[] source;
   private final int off;
   private final int length;
   
   public TextSegment(char[] source, int off, int length) {
      this.text = new String(source, off, length);
      this.source = source;
      this.length = length;
      this.off = off;         
   }
   
   @Override
   public void process(Scope scope, Writer writer) throws Exception {
      writer.write(source, off, length);
   } 
   
   @Override
   public String toString() {
      return text;
   }
}
