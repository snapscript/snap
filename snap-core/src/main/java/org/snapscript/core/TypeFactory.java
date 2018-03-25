package org.snapscript.core;

@Bug("this is a crap name")
public abstract class TypeFactory {

   public void define(Scope scope, Type type) throws Exception {} 
   public void compile(Scope scope, Type type) throws Exception {}
   public void allocate(Scope scope, Type type) throws Exception {} // static stuff
   
   public Result execute(Scope scope, Type type) throws Exception { // instance stuff
      return Result.getNormal();
   }
}