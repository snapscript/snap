package org.snapscript.core;

public abstract class Statement {
   
   public Result define(Scope scope) throws Exception {
      return Result.getNormal();
   }
                  
   public Result compile(Scope scope) throws Exception {
      return Result.getNormal();
   }
   
   public Result execute(Scope scope) throws Exception {
      return Result.getNormal();
   }
}