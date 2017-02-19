/*
 * OriginTraceExtractor.java December 2016
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

package org.snapscript.core.stack;

import static org.snapscript.core.Reserved.IMPORT_JAVA;
import static org.snapscript.core.Reserved.IMPORT_SNAPSCRIPT;

import java.util.ArrayList;
import java.util.List;

public class OriginTraceExtractor {
   
   public static final int DEFAULT_DEPTH = 0;
   public static final int DEBUG_DEPTH = 2; 

   private final int depth;
   
   public OriginTraceExtractor() {
      this(DEFAULT_DEPTH);
   }
   
   public OriginTraceExtractor(int depth) {
      this.depth = depth;
   }

   public List<StackTraceElement> extract(Throwable cause) {
      List<StackTraceElement> list = new ArrayList<StackTraceElement>();
   
      if(cause != null) {
         StackTraceElement[] elements = cause.getStackTrace();
         
         for(int i = 0; i < depth; i++) {
            StackTraceElement element = elements[i];
            String source = element.getClassName();
            
            if(source.startsWith(IMPORT_SNAPSCRIPT)) { 
               list.add(element);
            } else if(source.startsWith(IMPORT_JAVA)) {
               list.add(element);
            } else {
               return list;
            }
         } 
      }
      return list;
   }
}
