package org.snapscript.compile.validate;

import java.util.List;

import org.snapscript.core.module.Module;
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
         try {
            validator.validate(type);
         }catch(Exception e) {
            throw new ValidateException("Invalid reference to '" + type +"' in '" + name + "'", e);
         }
      }
   }
}