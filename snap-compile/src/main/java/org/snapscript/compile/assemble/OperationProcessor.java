package org.snapscript.compile.assemble;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.module.FilePathConverter;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.ModuleRegistry;
import org.snapscript.core.module.Path;
import org.snapscript.core.module.PathConverter;
import org.snapscript.parse.Line;

public class OperationProcessor {

   private final PathConverter converter;
   private final Context context;
   
   public OperationProcessor(Context context) {
      this.converter = new FilePathConverter();
      this.context = context;
   }
   
   public Object process(Object value, Line line) throws Exception {
      if(Compilation.class.isInstance(value)) {
         Compilation compilation = (Compilation)value;
         String resource = line.getResource();
         Path path = converter.createPath(resource);
         String name = converter.createModule(resource);
         ModuleRegistry registry = context.getRegistry();
         Module module = registry.addModule(name);
         int number = line.getNumber();
         
         return compilation.compile(module, path, number);
      }
      return value;
   }
}