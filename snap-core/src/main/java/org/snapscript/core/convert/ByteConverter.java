/*
 * ByteConverter.java December 2016
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

package org.snapscript.core.convert;

import static org.snapscript.core.convert.Score.COMPATIBLE;
import static org.snapscript.core.convert.Score.EXACT;
import static org.snapscript.core.convert.Score.SIMILAR;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.snapscript.core.Type;

public class ByteConverter extends NumberConverter {

   private static final Class[] BYTE_TYPES = {
      Byte.class, 
      Short.class, 
      Integer.class, 
      AtomicInteger.class, 
      Long.class,
      AtomicLong.class, 
      BigInteger.class, 
      Double.class, 
      Float.class, 
      BigDecimal.class
   };
   
   private static final Score[] BYTE_SCORES = {
      EXACT,
      SIMILAR,
      SIMILAR,
      SIMILAR,
      SIMILAR,
      SIMILAR,
      SIMILAR,
      COMPATIBLE,
      COMPATIBLE,
      COMPATIBLE
   };
   
   public ByteConverter(Type type) {
      super(type, BYTE_TYPES, BYTE_SCORES);
   }
}