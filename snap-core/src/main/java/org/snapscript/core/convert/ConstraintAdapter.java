/*
 * ConstraintAdapter.java December 2016
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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ConstraintAdapter {

   public Double createDouble(String text) {
      return Double.parseDouble(text);
   }
   
   public Float createFloat(String text) {
      return (float)Double.parseDouble(text);
   }
   
   public Integer createInteger(String text) {
      return (int)Double.parseDouble(text);
   }
   
   public Long createLong(String text) {
      return (long)Double.parseDouble(text);
   }
   
   public Short createShort(String text) {
      return (short)Double.parseDouble(text);
   }
   
   public Byte createByte(String text) {
      return (byte)Double.parseDouble(text);
   }
   
   public BigDecimal createBigDecimal(String text) {
      return new BigDecimal(text);
   }
   
   public BigInteger createBigInteger(String text) {
      return new BigDecimal(text).toBigInteger();
   }
   
   public AtomicLong createAtomicLong(String text) {
      long value = (long)Double.parseDouble(text);
      return new AtomicLong(value);
   }
   
   public AtomicInteger createAtomicInteger(String text) {
      int value = (int)Double.parseDouble(text);
      return new AtomicInteger(value);
   }
   
   public Boolean createBoolean(String text) {
      return Boolean.parseBoolean(text);
   }
   
   public Character createCharacter(String text) {
      return text.charAt(0);
   }
   
   public Double createDouble(Number number) {
      return number.doubleValue();
   }
   
   public Float createFloat(Number number) {
      return number.floatValue();
   }
   
   public Integer createInteger(Number number) {
      return number.intValue();
   }
   
   public Long createLong(Number number) {
      return number.longValue();
   }
   
   public Short createShort(Number number) {
      return number.shortValue();
   }
   
   public Byte createByte(Number number) {
      return number.byteValue();
   }
   
   public BigDecimal createBigDecimal(Number number) {
      double value = number.doubleValue();
      return BigDecimal.valueOf(value);
   }
   
   public BigInteger createBigInteger(Number number) {
      long value = number.longValue();
      return BigInteger.valueOf(value);
   }
   
   public AtomicLong createAtomicLong(Number number) {
      long value = number.longValue();
      return new AtomicLong(value);
   }
   
   public AtomicInteger createAtomicInteger(Number number) {
      int value = number.intValue();
      return new AtomicInteger(value);
   }
   
   public Character createCharacter(Number number) {
      char value = (char)number.shortValue();
      return Character.valueOf(value);
   }
}
