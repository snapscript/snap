/*
 * BitSet.java December 2016
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

package org.snapscript.common;

public class BitSet {

   private final long[] bits;
   
   public BitSet(int length){
      this.bits = new long[length / 64 + 1];
   }
   
   public void set(int index){
      int bit = index % 64;
      long mask = 1L << bit;
      
      bits[index / 64] |= mask;
      
   }
   
   public boolean get(int index){
      int bit = index % 64;
      long mask = 1L << bit;
      long word = bits[index / 64];
      
      return (word & mask) != 0;
   }
}
