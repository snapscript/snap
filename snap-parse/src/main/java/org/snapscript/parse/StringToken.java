/*
 * StringToken.java December 2016
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

public class StringToken implements Token<String>{
   
   private final String value;
   private final Line line;
   private final short type;
   
   public StringToken(String value) {
      this(value, null, 0);
   }
   
   public StringToken(String value, Line line, int type) {
      this.type = (short)type;
      this.value = value;
      this.line = line;
   }
   
   @Override
   public String getValue() {
      return value;
   }
   
   @Override
   public Line getLine() {
      return line;
   }
   
   @Override
   public short getType() {
      return type;
   }
}
