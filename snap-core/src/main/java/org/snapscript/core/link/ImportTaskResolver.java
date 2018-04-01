package org.snapscript.core.link;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.snapscript.core.module.FilePathConverter;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;
import org.snapscript.core.module.PathConverter;
import org.snapscript.core.type.Type;

public class ImportTaskResolver {

   private final ConcurrentMap<Path, Future<Module>> modules;
   private final ConcurrentMap<Path, Future<Type>> types;
   private final ImportTaskBuilder builder;
   private final PathConverter converter;
   private final Executor executor;
   
   public ImportTaskResolver(Module parent, Executor executor, Path from) {
      this.modules = new ConcurrentHashMap<Path, Future<Module>>();
      this.types = new ConcurrentHashMap<Path, Future<Type>>();
      this.builder = new ImportTaskBuilder(parent, from);
      this.converter = new FilePathConverter();
      this.executor = executor;
   }
   
   public Future<Type> importType(String name) throws Exception{
      Path path = converter.createPath(name);
      Callable<Type> task = builder.createType(name, path);
      
      if(task != null) {
         FutureTask<Type> future = new FutureTask<Type>(task);
         
         if(types.putIfAbsent(path, future) == null) {
            if(executor != null) {
               executor.execute(future); // reduce the stack depth
            } else {
               future.run();
            }
            return future;
         }
         return types.get(path);
      }
      return null;
   }
   
   public Future<Module> importModule(String name) throws Exception{
      Path path = converter.createPath(name);
      Callable<Module> task = builder.createModule(name, path);
      
      if(task != null) {
         FutureTask<Module> future = new FutureTask<Module>(task);
         
         if(modules.putIfAbsent(path, future) == null) {
            if(executor != null) {
               executor.execute(future); // reduce the stack depth
            } else {
               future.run();
            }
            return future;
         }
         return modules.get(path);
      }
      return null;
   }
}