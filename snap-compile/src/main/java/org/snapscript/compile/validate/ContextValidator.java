package org.snapscript.compile.validate;

import java.util.List;

import org.snapscript.compile.verify.Verifier;
import org.snapscript.core.ApplicationValidator;
import org.snapscript.core.Context;
import org.snapscript.core.convert.ConstraintMatcher;
import org.snapscript.core.function.index.FunctionIndexer;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.ModuleRegistry;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeExtractor;

public class ContextValidator implements ApplicationValidator {

   private final ModuleValidator modules;
   private final TypeValidator types;
   private final Verifier verifier;
   
   public ContextValidator(ConstraintMatcher matcher, TypeExtractor extractor, FunctionIndexer indexer, Verifier verifier) {
      this.types = new TypeValidator(matcher, extractor, indexer);
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