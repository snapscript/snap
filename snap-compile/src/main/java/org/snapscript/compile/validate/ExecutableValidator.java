package org.snapscript.compile.validate;

import java.util.List;

import org.snapscript.compile.verify.Verifier;
import org.snapscript.core.Context;
import org.snapscript.core.ContextValidator;
import org.snapscript.core.constraint.transform.ConstraintTransformer;
import org.snapscript.core.convert.ConstraintMatcher;
import org.snapscript.core.function.index.FunctionIndexer;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.ModuleRegistry;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeExtractor;

public class ExecutableValidator implements ContextValidator {

   private final ModuleValidator modules;
   private final TypeValidator types;
   private final Verifier verifier;
   
   public ExecutableValidator(ConstraintMatcher matcher, ConstraintTransformer transformer, TypeExtractor extractor, FunctionIndexer indexer, Verifier verifier) {
      this.types = new TypeValidator(matcher, transformer, extractor, indexer);
      this.modules = new ModuleValidator(types);
      this.verifier = verifier;
   }
   
   @Override
   public void validate(Context context) throws Exception {
      ModuleRegistry registry = context.getRegistry();
      List<Module> available = registry.getModules();
    
      verifier.verify();
    
      try {
         for(Module module : available) {
            modules.validate(module);
         }  
      } finally {
         verifier.verify();
      }
   }

   @Override
   public void validate(Type type) throws Exception {
      try {
         types.validate(type);
      } finally {
         verifier.verify();
      }
   }

   @Override
   public void validate(Module module) throws Exception {
      try {
         modules.validate(module);
      } finally {
         verifier.verify();
      }
   }
}