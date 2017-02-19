/*
 * StackElementConverter.java December 2016
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

import java.util.LinkedList;
import java.util.List;

import org.snapscript.common.Stack;

public class StackElementConverter {
   
   public StackElementConverter() {
      super();
   }
   
   public List<StackTraceElement> create(Stack stack) {
      List<StackTraceElement> list = new LinkedList<StackTraceElement>();
      StackElementIterator iterator = new StackElementIterator(stack);
      
      while(iterator.hasNext()) {
         StackElement next = iterator.next();
         
         if(next != null) {
            StackTraceElement trace = next.build();
         
            if(trace != null) {
               list.add(trace);
            }
         }
      }
      return list;
   }
}
