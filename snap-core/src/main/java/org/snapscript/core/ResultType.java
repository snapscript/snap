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