package org.snapscript.compile.assemble;

import static org.snapscript.tree.Instruction.SCRIPT_PACKAGE;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;

import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Path;
import org.snapscript.core.Scope;
import org.snapscript.core.link.Package;
import org.snapscript.core.link.PackageDefinition;
import org.snapscript.core.link.PackageLinker;

public class ExecutorLinker implements PackageLinker {
   
   private final ConcurrentMap<Path, Package> registry;
   private final PackageLinker linker;
   private final Executor executor;
   
   public ExecutorLinker(Context context) {
      this(context, null);
   }
   
   public ExecutorLinker(Context context, Executor executor) {
      this.registry = new ConcurrentHashMap<Path, Package>();
      this.linker = new ProgramLinker(context);
      this.executor = executor;
   }

   @Override
   public Package link(Path path, String source) throws Exception {
      return link(path, source, SCRIPT_PACKAGE.name);
   }

   @Override
   public Package link(Path path, String source, String grammar) throws Exception {
      if(executor != null) {
         PackageCompilation compilation = new PackageCompilation(path, source, grammar);
         FutureTask<Package> task = new FutureTask<Package>(compilation);
         FuturePackage result = new FuturePackage(task, path);
         
         if(registry.putIfAbsent(path, result) == null) {
            executor.execute(task); 
            return result;
         }
         return registry.get(path);
      }
      return linker.link(path, source, grammar);
   }
   
   private class FuturePackage implements Package {
      
      private final FutureTask<Package> result;
      private final Path path;
      
      public FuturePackage(FutureTask<Package> result, Path path) {
         this.result = result;
         this.path = path;
      }
      
      @Override
      public PackageDefinition define(Scope scope) throws Exception {
         Package library = result.get();
         
         if(library == null) {
            throw new InternalStateException("Could not link '" + path + "'");
         }
         return library.define(scope);
      }      
   }
   
   private class PackageCompilation implements Callable<Package> {      
      
      private final String grammar;
      private final String source;  
      private final Path path;
      
      public PackageCompilation(Path path, String source) {
         this(path, source, null);
      }
      
      public PackageCompilation(Path path, String source, String grammar) {
         this.grammar = grammar;
         this.source = source;
         this.path = path;
      }

      @Override
      public Package call() {
         try {               
            if(grammar != null) {
               return linker.link(path, source, grammar);
            }
            return linker.link(path, source);
         } catch(Exception cause) {
            return new ExceptionPackage("Could not link '" + path +"'", cause);
         } 
      }            
   }
   
   private class ExceptionPackage implements Package {
      
      private final Exception cause;
      private final String message;
      
      public ExceptionPackage(String message, Exception cause) {
         this.message = message;
         this.cause = cause;
      }  
      
      @Override
      public PackageDefinition define(Scope scope) throws Exception {
         throw new InternalStateException(message, cause);
      }             
   }

}
