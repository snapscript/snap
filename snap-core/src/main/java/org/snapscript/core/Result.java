package org.snapscript.core;

import static org.snapscript.core.ResultType.BREAK;
import static org.snapscript.core.ResultType.CONTINUE;
import static org.snapscript.core.ResultType.DECLARE;
import static org.snapscript.core.ResultType.NORMAL;
import static org.snapscript.core.ResultType.RETURN;
import static org.snapscript.core.ResultType.THROW;
import static org.snapscript.core.ResultType.YIELD;

import org.snapscript.core.yield.Resume;
import org.snapscript.core.yield.YieldResult;

public class Result {
   
   public static final Result NORMAL_RESULT = new Result(NORMAL);
   public static final Result RETURN_RESULT = new Result(RETURN);
   public static final Result BREAK_RESULT = new Result(BREAK);
   public static final Result CONTINUE_RESULT = new Result(CONTINUE);
   public static final Result THROW_RESULT = new Result(THROW);
   public static final Result DECLARE_RESULT = new Result(DECLARE);
   
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
   
   public static Result getYield(){
      return new YieldResult();
   }
   
   public static Result getYield(Object value) {
      return new YieldResult(value);
   }
   
   public static Result getYield(Object value, Scope scope, Resume next) {
      return new YieldResult(value, scope, next);
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
   
   protected final ResultType type;
   protected final Object value;

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
   
   public boolean isYield() {
      return type == YIELD;
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

   public <T> T getValue() {
      return (T)value;
   }
}