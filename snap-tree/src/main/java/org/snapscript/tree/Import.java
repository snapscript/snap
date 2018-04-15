package org.snapscript.tree;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Execution;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.NoStatement;
import org.snapscript.core.Statement;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.link.ImportManager;
import org.snapscript.core.link.Package;
import org.snapscript.core.link.PackageDefinition;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.NameBuilder;
import org.snapscript.core.type.TypeLoader;
import org.snapscript.core.variable.Value;

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
   public Statement compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      TypeLoader loader = context.getLoader();
      String location = qualifier.getLocation();
      String target = qualifier.getTarget();
      String name = qualifier.getName();
      
      if(target == null) {
         Package library = loader.importPackage(location);
         
         if(library != null) {
            return new CompileResult(library, path, location, null, name);
         }
      }
      Package library = loader.importType(location, target);

      if(library != null) {
         if(alias != null) {
            Scope scope = module.getScope();
            Value value = alias.evaluate(scope, null);
            String alias = value.getString();
            
            return new CompileResult(library, path, location, target, alias);
         } 
         return new CompileResult(library, path, location, target, target, name);
      }
      return new NoStatement();
   }
   
   private static class CompileResult extends Statement {
      
      private PackageDefinition definition;
      private NameBuilder builder;
      private Statement statement;
      private Package library;
      private Path path;
      private String location;
      private String target;
      private String[] alias;

      public CompileResult(Package library, Path path, String location, String target, String... alias) {
         this.builder = new NameBuilder();
         this.location = location;
         this.library = library;
         this.target = target;
         this.alias = alias;
         this.path = path;
      }
      
      @Override
      public void create(Scope scope) throws Exception {
         if(library == null) {
            throw new InternalStateException("Import '" + location + "' was not loaded");
         }
         if(definition == null) { // define once
            definition = process(scope);
         }
      }

      @Override
      public boolean define(Scope scope) throws Exception {
         if(definition == null) {
            throw new InternalStateException("Import '" + location + "' was not defined");
         }
         if(statement == null) { // compile once
            statement = definition.define(scope, path);
         }
         return true;
      }
      
      @Override
      public Execution compile(Scope scope, Constraint returns) throws Exception {
         if(statement == null) {
            throw new InternalStateException("Import '" + location + "' was not compiled");
         }
         return statement.compile(scope, returns); // execute many times
      }
      
      private PackageDefinition process(Scope scope) throws Exception {
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
         return library.create(scope);
      }
   }

}