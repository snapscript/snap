/*
 * LengthComparator.java December 2016
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

package org.snapscript.parse;

import java.util.Comparator;

public class LengthComparator implements Comparator<String>{
   
   private boolean reverse;
   
   public LengthComparator() {
      this(false);
   }
   
   public LengthComparator(boolean reverse) {
      this.reverse = reverse;
   }

   @Override
   public int compare(String left, String right) {
      Integer leftLength = left.length();
      Integer rightLength = right.length();
      
      if(reverse) {
         return leftLength.compareTo(rightLength);
      }
      return rightLength.compareTo(leftLength);
   }
}
