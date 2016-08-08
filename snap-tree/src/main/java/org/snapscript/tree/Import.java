package org.snapscript.tree;

import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Module;
import org.snapscript.core.NoStatement;
import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.TypeLoader;
import org.snapscript.core.Value;
import org.snapscript.core.index.TypeNameBuilder;
import org.snapscript.core.link.ImportManager;
import org.snapscript.core.link.Package;

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
      
      if(target == null) {
         Package library = loader.importPackage(location);
         
         if(library != null) {
            return new CompileResult(library, location);
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
         return new CompileResult(library, location, target);
      }
      return new NoStatement();
   }
   
   private static class CompileResult extends Statement {
      
      private final AtomicReference<Statement> reference;
      private final TypeNameBuilder builder;
      private final Package library;
      private final String location;
      private final String target;
      private final String alias;
      
      public CompileResult(Package library, String location) {
         this(library, location, null);
      }
      
      public CompileResult(Package library, String location, String target) {
         this(library, location, target, null);
      }
      
      public CompileResult(Package library, String location, String target, String alias) {
         this.reference = new AtomicReference<Statement>();
         this.builder = new TypeNameBuilder();
         this.location = location;
         this.library = library;
         this.target = target;
         this.alias = alias;
      }
      
      @Override
      public Result compile(Scope scope) throws Exception {
         Statement statement = create(scope);
         
         if(statement != null) {
            reference.set(statement);
         }
         return ResultType.getNormal();
      }
      
      @Override
      public Result execute(Scope scope) throws Exception {
         Statement statement = reference.get();
         
         if(statement != null) {
            return statement.execute(scope);
         }
         return ResultType.getNormal();
      }
      
      private Statement create(Scope scope) throws Exception {
         Module module = scope.getModule();
         ImportManager manager = module.getManager();
         String type = builder.createName(location, target);
             
         if(target == null) {
            manager.addImport(location); // import game.tetris.*;
         }  else {
            if(alias != null) {
               manager.addImport(type, alias); // import game.tetris.Block as Shape;
            } else {
               manager.addImport(type, target); // import game.tetris.Block;
            }
         }
         return library.compile(scope);
      }
   }

}
