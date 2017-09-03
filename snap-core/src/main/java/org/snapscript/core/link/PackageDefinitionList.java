package org.snapscript.core.link;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.Path;
import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;

public class PackageDefinitionList implements PackageDefinition {
   
   private final List<PackageDefinition> definitions;
   
   public PackageDefinitionList(List<PackageDefinition> definitions) {
      this.definitions = definitions;
   }

   @Override
   public Statement compile(Scope scope, Path from) throws Exception {
      List<Statement> statements = new ArrayList<Statement>();
      
      for(PackageDefinition definition : definitions) {
         Statement statement = definition.compile(scope, from);
         
         if(statement != null) {
            statements.add(statement);
         }
      }
      return new StatementList(statements);
   }
   
   private class StatementList extends Statement {
      
      private final List<Statement> statements;
      
      public StatementList(List<Statement> statements) {
         this.statements = statements;
      }
      
      public Result define(Scope scope) throws Exception {
         Result last = Result.getNormal();
         
         for(Statement statement : statements){
            Result result = statement.define(scope);
         
            if(!last.isDeclare()) {
               last = result;
            }
            last = result;
         }
         return last;
      }
                     
      public Result compile(Scope scope) throws Exception {
         Result last = Result.getNormal();
         
         for(Statement statement : statements){
            Result result = statement.compile(scope);
         
            if(!last.isDeclare()) {
               last = result;
            }
            last = result;
         }
         return last;
      }
      
      public Result execute(Scope scope) throws Exception {
         Result result = Result.getNormal();
         
         for(Statement statement : statements){
            Result next = statement.execute(scope);
         
            if(!next.isNormal()){
               return next;
            }
            result = next;
         }
         return result;
      }
   }

}