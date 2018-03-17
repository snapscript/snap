package org.snapscript.core;

public abstract class Statement {
   
   public void create(Scope scope) throws Exception {}
   public void define(Scope scope) throws Exception {}
   public void compile(Scope scope) throws Exception {}
   
   public Result execute(Scope scope) throws Exception {
      return Result.getNormal();
   }
}