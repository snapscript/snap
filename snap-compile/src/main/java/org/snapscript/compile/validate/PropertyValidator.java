package org.snapscript.compile.validate;

import org.snapscript.core.convert.ConstraintMatcher;
import org.snapscript.core.property.Property;
import org.snapscript.core.type.Type;

public class PropertyValidator {
   
   private final ConstraintMatcher matcher;
   
   public PropertyValidator(ConstraintMatcher matcher) {
      this.matcher = matcher;
   }
   
   public void validate(Property property) throws Exception {
      Type source = property.getSource();

      if(source == null) {
         throw new ValidateException("Property '" + property + "' does not have a type");
      }
   }   
}