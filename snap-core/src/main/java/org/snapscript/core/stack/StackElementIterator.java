/*
 * StackElementIterator.java December 2016
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

import java.util.Iterator;

import org.snapscript.common.Stack;
import org.snapscript.core.function.Function;
import org.snapscript.core.trace.Trace;

public class StackElementIterator {
   
   private final Iterator iterator;
   
   public StackElementIterator(Stack stack) {
      this.iterator = stack.iterator();
   }
   
   public boolean hasNext() {
      return iterator.hasNext();
   }
   
   public StackElement next() {
      while(iterator.hasNext()) {
         Object value = iterator.next();
         
         if(Trace.class.isInstance(value)) {
            Trace trace = (Trace)value;
            
            while(iterator.hasNext()) {
               Object next = iterator.next();
               
               if(Function.class.isInstance(next)) {
                  return new StackElement(trace, (Function)next);
               }
            }
            return new StackElement(trace);
         }
      }
      return null;
   }
   

}