/*
 * PropertyValidator.java December 2016
 *
 * Copyright (C) 2016, Niall Gallagher <niallg@users.sf.net>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
 * implied. See the License for the specific language governing 
 * permissions and limitations under the License.
 */

package org.snapscript.compile.validate;

import static org.snapscript.core.Reserved.ENUM_NAME;
import static org.snapscript.core.Reserved.ENUM_ORDINAL;
import static org.snapscript.core.Reserved.ENUM_VALUES;
import static org.snapscript.core.Reserved.TYPE_CLASS;
import static org.snapscript.core.Reserved.TYPE_THIS;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Type;
import org.snapscript.core.convert.ConstraintMatcher;
import org.snapscript.core.property.Property;
import org.snapscript.tree.ModifierValidator;

public class PropertyValidator {
   
   private final ModifierValidator validator;
   private final String[] ignores;
   
   public PropertyValidator(ConstraintMatcher matcher) {
      this(matcher, TYPE_CLASS, TYPE_THIS, ENUM_NAME, ENUM_ORDINAL, ENUM_VALUES);
   }
   
   public PropertyValidator(ConstraintMatcher matcher, String... ignores) {
      this.validator = new ModifierValidator();
      this.ignores = ignores;
   }
   
   public void validate(Property property) throws Exception {
      Type type = property.getType();
      String name = property.getName();
      int modifiers = property.getModifiers();
      int matches = 0;
      
      for(String ignore : ignores) {
         if(ignore.equals(name)) {
            matches++;
         }
      }
      if(matches == 0) {
         if(type == null) {
            throw new InternalStateException("Property '" + property + "' does not have a type");
         }
         validator.validate(type, property, modifiers);
      }
   }
   
}
