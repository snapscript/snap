package org.snapscript.compile.validate;

import java.util.List;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Type;
import org.snapscript.core.convert.ConstraintMatcher;

public class ModuleValidator {

   private final TypeValidator validator;
   
   public ModuleValidator(ConstraintMatcher matcher) {
      this.validator = new TypeValidator(matcher);
   }
   
   public void validate(Module module) throws Exception {
      List<Type> types = module.getTypes();
      String resource = module.getPath();
      
      for(Type type : types) {
         Module parent = type.getModule();
         String prefix = parent.getName();
         String name = type.getName();

         try {
            validator.validate(type);
         }catch(Exception e) {
            throw new InternalStateException("Invalid reference to '" + prefix + "." + name +"' in '" + resource + "'", e);
         }
      }
   }
}
