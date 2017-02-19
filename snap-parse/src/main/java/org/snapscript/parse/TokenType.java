/*
 * TokenType.java December 2016
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

public enum TokenType {
   IDENTIFIER(0, 0x0001),
   TYPE(1, 0x0002),   
   QUALIFIER(2, 0x0004),   
   HEXIDECIMAL(3, 0x0008),   
   BINARY(4, 0x0010),  
   DECIMAL(5, 0x0020),
   TEXT(6, 0x0040),
   LITERAL(7, 0x0080),
   TEMPLATE(8, 0x0100);
   
   public final short mask;
   public final int index;
   
   private TokenType(int index, int mask) {
      this.mask = (short)mask;
      this.index = index;
   }
   
   public int getIndex(){
      return index;
   }
   
   public int getMask(){
      return mask;
   }
}
