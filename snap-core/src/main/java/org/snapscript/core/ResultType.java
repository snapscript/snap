/*
 * ResultType.java December 2016
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

import static org.snapscript.core.Result.BREAK_RESULT;
import static org.snapscript.core.Result.CONTINUE_RESULT;
import static org.snapscript.core.Result.DECLARE_RESULT;
import static org.snapscript.core.Result.NORMAL_RESULT;
import static org.snapscript.core.Result.RETURN_RESULT;

public enum ResultType{
   RETURN,
   THROW,
   BREAK,
   CONTINUE,
   DECLARE,
   NORMAL;
   
   public static Result getDeclare(){
      return DECLARE_RESULT;
   }
   
   public static Result getNormal(){
      return NORMAL_RESULT;
   }
   
   public static Result getNormal(Object value) {
      return new Result(NORMAL, value);
   }
   
   public static Result getReturn(){
      return RETURN_RESULT;
   }
   
   public static Result getReturn(Object value) {
      return new Result(RETURN, value);
   }
   
   public static Result getBreak() {
      return BREAK_RESULT;
   }
   
   public static Result getContinue() {
      return CONTINUE_RESULT;
   }
   
   public static Result getThrow(Object value) {
      return new Result(THROW, value);
   }
}