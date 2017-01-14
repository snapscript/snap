package org.snapscript.tree;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.NameBuilder;
import org.snapscript.core.NoStatement;
import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.TypeLoader;
import org.snapscript.core.TypeNameBuilder;
import org.snapscript.core.Value;
import org.snapscript.core.link.ImportManager;
import org.snapscript.core.link.Package;
import org.snapscript.core.link.PackageDefinition;

public class Import implements Compilation {

   private final Qualifier qualifier;
   private final Evaluation alias;   
   
   public Import(Qualifier qualifier) {
      this(qualifier, null);
   }
   
   public Import(Qualifier qualifier, Evaluation alias) {
      this.qualifier = qualifier;
      this.alias = alias;
   }
   
   @Override
   public Object compile(Module module, int line) throws Exception {
      Context context = module.getContext();
      TypeLoader loader = context.getLoader();
      String location = qualifier.getLocation();
      String target = qualifier.getTarget();
      String name = qualifier.getName();
      
      if(target == null) {
         Package library = loader.importPackage(location);
         
         if(library != null) {
            return new CompileResult(library, location, null, name);
         }
      }
      Package library = loader.importType(location, target);

      if(library != null) {
         if(alias != null) {
            Scope scope = module.getScope();
            Value value = alias.evaluate(scope, null);
            String alias = value.getString();
            
            return new CompileResult(library, location, target, alias);
         } 
         return new CompileResult(library, location, target, target, name);
      }
      return new NoStatement();
   }
   
   private static class CompileResult extends Statement {
      
      private PackageDefinition definition;
      private NameBuilder builder;
      private Statement statement;
      private Package library;
      private String location;
      private String target;
      private String[] alias;
      
      public CompileResult(Package library, String location) {
         this(library, location, null);
      }
      
      public CompileResult(Package library, String location, String target) {
         this(library, location, target, null);
      }
      
      public CompileResult(Package library, String location, String target, String... alias) {
         this.builder = new TypeNameBuilder();
         this.location = location;
         this.library = library;
         this.target = target;
         this.alias = alias;
      }
      
      @Override
      public Result define(Scope scope) throws Exception {
         if(library == null) {
            throw new InternalStateException("Import '" + location + "' was not loaded");
         }
         if(definition == null) { // define once
            definition = create(scope);
         }
         return ResultType.getNormal(); 
      }

      @Override
      public Result compile(Scope scope) throws Exception {
         if(definition == null) {
            throw new InternalStateException("Import '" + location + "' was not defined");
         }
         if(statement == null) { // compile once
            statement = definition.compile(scope);
         }
         return ResultType.getNormal();
      }
      
      @Override
      public Result execute(Scope scope) throws Exception {
         if(statement == null) {
            throw new InternalStateException("Import '" + location + "' was not compiled");
         }
         return statement.execute(scope); // execute many times
      }
      
      private PackageDefinition create(Scope scope) throws Exception {
         Module module = scope.getModule();
         ImportManager manager = module.getManager();
         String type = builder.createFullName(location, target);
             
         if(target == null) {
            manager.addImport(location); // import game.tetris.*;
         }  else {
            if(alias != null) {
               for(String name : alias) {
                  if(name != null) {
                     manager.addImport(type, name); // import game.tetris.Block as Shape;
                  }
               }
            } else {
               manager.addImport(type, target); // import game.tetris.Block;
            }
         }
         return library.define(scope);
      }
   }

}
