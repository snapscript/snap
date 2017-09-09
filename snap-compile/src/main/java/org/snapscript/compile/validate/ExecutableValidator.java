package org.snapscript.compile.validate;

import java.util.List;

import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.ModuleRegistry;
import org.snapscript.core.ProgramValidator;
import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.bind.FunctionResolver;
import org.snapscript.core.bind2.FunctionResolver2;
import org.snapscript.core.convert.ConstraintMatcher;

public class ExecutableValidator implements ProgramValidator {

   private final ModuleValidator modules;
   private final TypeValidator types;
   
   public ExecutableValidator(ConstraintMatcher matcher, TypeExtractor extractor, FunctionResolver resolver, FunctionResolver2 resolver2) {
      this.types = new TypeValidator(matcher, extractor, resolver, resolver2);
      this.modules = new ModuleValidator(types);
   }
   
   @Override
   public void validate(Context context) throws Exception {
      ModuleRegistry registry = context.getRegistry();
      List<Module> modules = registry.getModules();
      
      for(Module module : modules) {
         validate(module);
      }
   }

   @Override
   public void validate(Type type) throws Exception {
      types.validate(type);
   }

   @Override
   public void validate(Module module) throws Exception {
      modules.validate(module);
   }
}