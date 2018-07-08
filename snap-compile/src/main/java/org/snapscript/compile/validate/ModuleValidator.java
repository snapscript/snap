package org.snapscript.compile.validate;

import static org.snapscript.core.type.Phase.COMPILE;

import java.util.List;

import org.snapscript.common.Progress;
import org.snapscript.core.module.Module;
import org.snapscript.core.type.Phase;
import org.snapscript.core.type.Type;

public class ModuleValidator {

   private final TypeValidator validator;
   
   public ModuleValidator(TypeValidator validator) {
      this.validator = validator;
   }
   
   public void validate(Module module) throws Exception {
      List<Type> types = module.getTypes();
      String name = module.getName();
      
      for(Type type : types) {
         Progress<Phase> progress = type.getProgress();
         
         try {
            progress.wait(COMPILE);
            validator.validate(type);
         }catch(Exception e) {
            throw new ValidateException("Invalid reference to '" + type +"' in '" + name + "'", e);
         }
      }
   }
}