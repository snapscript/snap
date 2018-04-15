package org.snapscript.core.type;

import static org.snapscript.core.result.Result.NORMAL;
import static org.snapscript.core.type.Order.OTHER;

import org.snapscript.core.result.Result;
import org.snapscript.core.scope.Scope;

public abstract class TypeState {

   public Order define(Scope scope, Type type) throws Exception {
      return OTHER;
   } 
   
   public void compile(Scope scope, Type type) throws Exception {}
   public void allocate(Scope scope, Type type) throws Exception {} // static stuff
   
   public Result execute(Scope scope, Type type) throws Exception { // instance stuff
      return NORMAL;
   }
}