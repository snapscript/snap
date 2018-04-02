package org.snapscript.compile;

import static org.snapscript.core.Reserved.DEFAULT_PACKAGE;
import static org.snapscript.tree.Instruction.SCRIPT;

import org.snapscript.compile.assemble.Application;
import org.snapscript.core.Context;
import org.snapscript.core.link.Package;
import org.snapscript.core.link.PackageLinker;
import org.snapscript.core.module.FilePathConverter;
import org.snapscript.core.module.Path;
import org.snapscript.core.module.PathConverter;

public class StringCompiler implements Compiler {
   
   private final PathConverter converter;
   private final Context context;   
   private final String module;
   
   public StringCompiler(Context context) {
      this(context, DEFAULT_PACKAGE);
   }
   
   public StringCompiler(Context context, String module) {
      this.converter = new FilePathConverter();
      this.context = context;
      this.module = module;
   } 
   
   @Override
   public Executable compile(String source) throws Exception {
      if(source == null) {
         throw new NullPointerException("No source provided");
      }
      Path path = converter.createPath(module);
      PackageLinker linker = context.getLinker();
      Package library = linker.link(path, source, SCRIPT.name);
      
      return new Application(context, library, path, module);
   } 
}