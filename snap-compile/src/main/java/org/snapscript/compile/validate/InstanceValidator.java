package org.snapscript.compile.validate;

import static org.snapscript.core.Reserved.TYPE_CLASS;
import static org.snapscript.core.Reserved.TYPE_THIS;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Model;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.Value;
import org.snapscript.core.State;
import org.snapscript.core.convert.ConstraintMatcher;

public class InstanceValidator {
   
   private static final String[] CONSTANTS = { TYPE_THIS, TYPE_CLASS };
   
   private final TypeValidator validator;
   
   public InstanceValidator(ConstraintMatcher matcher, TypeExtractor extractor) {
      this.validator = new TypeValidator(matcher, extractor);
   }
   
   public void validateInstance(Scope instance) throws Exception {
      State state = instance.getState();
      Type type = instance.getType();
      Model model = instance.getModel();
      
      if(model == null) {
         throw new InternalStateException("Instance of '" + type+ "' does not reference model");
      }
      for(String constant : CONSTANTS) {
         Value value = state.get(constant);
         
         if(value == null) {
            throw new InternalStateException("Constant '" + constant + "' not defined for '" + type+ "'");
         }
         Object object = value.getValue();
         
         if(object == null) {
            throw new InternalStateException("Constant '" + constant + "' not set for '" + type+ "'");
         }
      }
      validator.validate(type);
   }

}
