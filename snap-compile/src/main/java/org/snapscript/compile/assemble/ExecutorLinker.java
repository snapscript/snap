package org.snapscript.compile.assemble;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;

import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.link.Package;
import org.snapscript.core.link.PackageLinker;

public class ExecutorLinker implements PackageLinker {
   
   private final ConcurrentMap<String, Package> registry;
   private final PackageLinker linker;
   private final Executor executor;
   
   public ExecutorLinker(Context context) {
      this(context, null);
   }
   
   public ExecutorLinker(Context context, Executor executor) {
      this.registry = new ConcurrentHashMap<String, Package>();
      this.linker = new ProgramLinker(context);
      this.executor = executor;
   }

   @Override
   public Package link(String resource, String source) throws Exception {
      if(executor != null) {
         PackageCompilation compilation = new PackageCompilation(resource, source);
         FutureTask<Package> task = new FutureTask<Package>(compilation);
         FuturePackage result = new FuturePackage(task, resource);
         
         if(registry.putIfAbsent(resource, result) == null) {
            executor.execute(task); 
            return result;
         }
         return registry.get(resource);
      }
      return linker.link(resource, source);
   }

   @Override
   public Package link(String resource, String source, String grammar) throws Exception {
      if(executor != null) {
         PackageCompilation compilation = new PackageCompilation(resource, source, grammar);
         FutureTask<Package> task = new FutureTask<Package>(compilation);
         FuturePackage result = new FuturePackage(task, resource);
         
         if(registry.putIfAbsent(resource, result) == null) {
            executor.execute(task); 
            return result;
         }
         return registry.get(resource);
      }
      return linker.link(resource, source, grammar);
   }
   
   private class FuturePackage implements Package {
      
      private final FutureTask<Package> result;
      private final String resource;
      
      public FuturePackage(FutureTask<Package> result, String resource) {
         this.resource = resource;
         this.result = result;
      }
      
      @Override
      public Statement compile(Scope scope) throws Exception {
         Package library = result.get();
         
         if(library == null) {
            throw new InternalStateException("Could not link " + resource);
         }
         return library.compile(scope);
      }      
   }
   
   private class PackageCompilation implements Callable<Package> {      
      
      private final String resource;
      private final String grammar;
      private final String source;     
      
      public PackageCompilation(String resource, String source) {
         this(resource, source, null);
      }
      
      public PackageCompilation(String resource, String source, String grammar) {
         this.resource = resource;
         this.grammar = grammar;
         this.source = source;
      }

      @Override
      public Package call() {
         try {               
            if(grammar != null) {
               return linker.link(resource, source, grammar);
            }
            return linker.link(resource, source);
         } catch(Exception cause) {
            return new ExceptionPackage("Could not link " + resource, cause);
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
      public Statement compile(Scope scope) throws Exception {
         throw new InternalStateException(message, cause);
      }             
   }

}
