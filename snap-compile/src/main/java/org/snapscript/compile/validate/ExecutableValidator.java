package org.snapscript.compile.validate;

import java.util.List;

import org.snapscript.compile.verify.Verifier;
import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.ModuleRegistry;
import org.snapscript.core.ProgramValidator;
import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.convert.ConstraintMatcher;
import org.snapscript.core.function.search.FunctionResolver;

public class ExecutableValidator implements ProgramValidator {

   private final ModuleValidator modules;
   private final TypeValidator types;
   private final Verifier verifier;
   
   public ExecutableValidator(ConstraintMatcher matcher, TypeExtractor extractor, FunctionResolver resolver, Verifier verifier) {
      this.types = new TypeValidator(matcher, extractor, resolver);
      this.modules = new ModuleValidator(types);
      this.verifier = verifier;
   }
   
   @Override
   public void validate(Context context) throws Exception {
      ModuleRegistry registry = context.getRegistry();
      List<Module> modules = registry.getModules();
    
      verifier.verify();
      
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