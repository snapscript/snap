package org.snapscript.compile;

import static org.snapscript.tree.Instruction.SCRIPT;

import org.snapscript.compile.assemble.Program;
import org.snapscript.core.Context;
import org.snapscript.core.EmptyModule;
import org.snapscript.core.FilePathConverter;
import org.snapscript.core.Module;
import org.snapscript.core.Path;
import org.snapscript.core.PathConverter;
import org.snapscript.core.ResourceManager;
import org.snapscript.core.error.ThreadExceptionHandler;
import org.snapscript.core.extend.ModuleExtender;
import org.snapscript.core.link.Package;
import org.snapscript.core.link.PackageLinker;

public class ResourceCompiler implements Compiler {

   private final ThreadExceptionHandler handler;
   private final ModuleExtender extender;
   private final PathConverter converter;
   private final Context context;   
   private final Module empty;
   
   public ResourceCompiler(Context context) {
      this.extender = new ModuleExtender(context);
      this.handler = new ThreadExceptionHandler();
      this.converter = new FilePathConverter();
      this.empty = new EmptyModule(context);
      this.context = context;
   } 
   
   @Override
   public Executable compile(String resource) throws Exception {
      if(resource == null) {
         throw new NullPointerException("No resource provided");
      }
      ResourceManager manager = context.getManager();
      String source = manager.getString(resource);
      
      extender.extend(empty); // avoid a deadlock
      handler.register(); // catch rogue exceptions

      return compile(resource, source);
   } 
   
   private Executable compile(String resource, String source) throws Exception {
      if(source == null) {
         throw new IllegalArgumentException("Resource '" + resource + "' not found");
      }
      String module = converter.createModule(resource);
      Path path = converter.createPath(resource);
      PackageLinker linker = context.getLinker();
      Package library = linker.link(path, source, SCRIPT.name);
  
      return new Program(context, library, path, module);
   }
}