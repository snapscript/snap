package org.snapscript.core.link;

import java.util.concurrent.FutureTask;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Path;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;

public class FutureStatement extends Statement {
   
   private final FutureTask<Statement> result;
   private final Path path;
   
   public FutureStatement(FutureTask<Statement> result, Path path) {
      this.result = result;
      this.path = path;
   }

   @Override
   public Result define(Scope scope) throws Exception {
      Statement definition = result.get();
      
      if(definition == null) {
         throw new InternalStateException("Could not define '" + path + "'");
      }
      return definition.define(scope);
   }
   
   @Override
   public Result compile(Scope scope) throws Exception {
      Statement definition = result.get();
      
      if(definition == null) {
         throw new InternalStateException("Could not compile '" + path + "'");
      }
      return definition.compile(scope);
   }
   
   @Override
   public Result execute(Scope scope) throws Exception {
      Statement definition = result.get();
      
      if(definition == null) {
         throw new InternalStateException("Could not execute '" + path + "'");
      }
      return definition.execute(scope);
   }      
}
