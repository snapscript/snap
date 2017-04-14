
package org.snapscript.core;

public abstract class TypeFactory {

   public Result compile(Scope scope, Type type) throws Exception { // static stuff
      return ResultType.getNormal();
   }
   
   public Result execute(Scope scope, Type type) throws Exception { // instance stuff
      return ResultType.getNormal();
   }
}