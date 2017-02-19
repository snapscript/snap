/*
 * Result.java December 2016
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

package org.snapscript.core;

import static org.snapscript.core.ResultType.BREAK;
import static org.snapscript.core.ResultType.CONTINUE;
import static org.snapscript.core.ResultType.DECLARE;
import static org.snapscript.core.ResultType.NORMAL;
import static org.snapscript.core.ResultType.RETURN;
import static org.snapscript.core.ResultType.THROW;

public class Result {
   
   public static final Result NORMAL_RESULT = new Result(NORMAL);
   public static final Result RETURN_RESULT = new Result(RETURN);
   public static final Result BREAK_RESULT = new Result(BREAK);
   public static final Result CONTINUE_RESULT = new Result(CONTINUE);
   public static final Result THROW_RESULT = new Result(THROW);
   public static final Result DECLARE_RESULT = new Result(DECLARE);
   
   private final ResultType type;
   private final Object value;

   public Result(ResultType type) {
      this(type, null);
   }

   public Result(ResultType type, Object value) {
      this.value = value;
      this.type = type;
   }
   
   public boolean isDeclare() {
      return type == DECLARE;
   }
   
   public boolean isReturn() {
      return type == RETURN;
   }
   
   public boolean isNormal() {
      return type == NORMAL || type == DECLARE;
   }
   
   public boolean isBreak() {
      return type == BREAK;
   }
   
   public boolean isThrow()  {
      return type == THROW;
   }
   
   public boolean isContinue() {
      return type == CONTINUE;
   }

   public ResultType getType() {
      return type;
   }

   public <T> T getValue() {
      return (T)value;
   }
}