/*
 * WildQualifier.java December 2016
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

package org.snapscript.tree;

import org.snapscript.parse.StringToken;

public class WildQualifier implements Qualifier {

   private final StringToken[] tokens;
   private final int count;

   public WildQualifier(StringToken... tokens) {
      this.count = tokens.length;
      this.tokens = tokens;
   }
   
   @Override
   public String[] getSegments() {
      String[] segments = new String[count];

      for (int i = 0; i < count; i++) {
         segments[i] = tokens[i].getValue();
      }
      return segments;
   }

   @Override
   public String getQualifier() {
      StringBuilder builder = new StringBuilder();

      for (int i = 0; i < count; i++) {
         String value = tokens[i].getValue();

         if (i > 0) {
            builder.append(".");
         }
         builder.append(value);
      }
      return builder.toString();
   }

   @Override
   public String getLocation() {
      StringBuilder builder = new StringBuilder();

      for (int i = 0; i < count; i++) {
         String value = tokens[i].getValue();
         char first = value.charAt(0);

         if(first >='A' && first <='Z') {
            return builder.toString();
         }
         if (i > 0) {
            builder.append(".");
         }
         builder.append(value);
      }
      return builder.toString();
   }

   @Override
   public String getTarget() {
      StringBuilder builder = new StringBuilder();

      for (int i = 1; i < count; i++) {
         String value = tokens[i].getValue();
         char first = value.charAt(0);

         if(first >='A' && first <='Z') {
            builder.append(value);
            
            while(++i < count) {
               value = tokens[i].getValue();
               first = value.charAt(0);
               
               if(first <'A' || first >'Z') {
                  return builder.toString();
               }
               builder.append("$");
               builder.append(value);
            }
            return builder.toString();
         }
      }
      return null;
   }
   
   @Override
   public String getName() {
      return null;
   }
}
