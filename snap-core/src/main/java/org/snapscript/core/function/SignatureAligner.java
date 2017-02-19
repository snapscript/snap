/*
 * SignatureAligner.java December 2016
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

package org.snapscript.core.function;

import java.lang.reflect.Array;
import java.util.List;

import org.snapscript.core.InternalStateException;

public class SignatureAligner {

   private final Signature signature;
   
   public SignatureAligner(Signature signature) {
      this.signature = signature;
   }
   
   public Object[] align(Object... list) throws Exception {
      if(signature.isVariable()) {
         List<Parameter> parameters = signature.getParameters();
         int length = parameters.size();
         int start = length - 1;
         int remaining = list.length - start;
         
         if(remaining > 0) {
            Object array = new Object[remaining];
            
            for(int i = 0; i < remaining; i++) {
               try {
                  Array.set(array, i, list[i + start]);
               } catch(Exception e){
                  throw new InternalStateException("Invalid argument at " + i + " for" + signature, e);
               }
            }
            list[start] = array;
         }
         Object[] copy = new Object[length];
         
         for(int i = 0; i < length; i++) {
            copy[i] = list[i];
         }
         return copy;
      }
      return list;
   }
}
