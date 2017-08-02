package org.snapscript.compile;

import static org.snapscript.tree.Instruction.SCRIPT;

import org.snapscript.common.Cache;
import org.snapscript.common.LeastRecentlyUsedCache;
import org.snapscript.compile.assemble.Program;
import org.snapscript.core.Context;
import org.snapscript.core.FilePathConverter;
import org.snapscript.core.Path;
import org.snapscript.core.PathConverter;
import org.snapscript.core.ResourceManager;
import org.snapscript.core.link.Package;
import org.snapscript.core.link.PackageLinker;

public class ResourceCompiler implements Compiler {

   private final Cache<String, Executable> cache;
   private final PathConverter converter;
   private final Context context;   
   
   public ResourceCompiler(Context context) {
      this.cache = new LeastRecentlyUsedCache<String, Executable>();
      this.converter = new FilePathConverter();
      this.context = context;
   } 
   
   @Override
   public Executable compile(String resource) throws Exception {
      if(resource == null) {
         throw new NullPointerException("No resource provided");
      }
      Executable executable = cache.fetch(resource);
      
      if(executable == null) {
         ResourceManager manager = context.getManager();
         String module = converter.createModule(resource);
         Path path = converter.createPath(resource);
         String source = manager.getString(resource);
         
         if(source == null) {
            throw new IllegalArgumentException("Resource '" + resource + "' not found");
         }
         PackageLinker linker = context.getLinker();
         Package library = linker.link(path, source, SCRIPT.name);
  
         return new Program(context, library, path, module);
      }
      return executable;
   } 
}