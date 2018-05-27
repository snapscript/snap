package org.snapscript.compile.validate;

import static org.snapscript.core.Reserved.ENUM_NAME;
import static org.snapscript.core.Reserved.ENUM_ORDINAL;
import static org.snapscript.core.Reserved.ENUM_VALUES;
import static org.snapscript.core.Reserved.TYPE_CLASS;
import static org.snapscript.core.Reserved.TYPE_THIS;

import org.snapscript.core.convert.ConstraintMatcher;
import org.snapscript.core.property.Property;
import org.snapscript.core.type.Type;

public class PropertyValidator {
   
   private final String[] ignores;
   
   public PropertyValidator(ConstraintMatcher matcher) {
      this(matcher, TYPE_CLASS, TYPE_THIS, ENUM_NAME, ENUM_ORDINAL, ENUM_VALUES);
   }
   
   public PropertyValidator(ConstraintMatcher matcher, String... ignores) {
      this.ignores = ignores;
   }
   
   public void validate(Property property) throws Exception {
      Type source = property.getSource();
      String name = property.getName();
      int matches = 0;
      
      for(String ignore : ignores) {
         if(ignore.equals(name)) {
            matches++;
         }
      }
      if(matches == 0) {
         if(source == null) {
            throw new ValidateException("Property '" + property + "' does not have a type");
         }
      }
   }
   
}