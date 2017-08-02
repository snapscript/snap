package org.snapscript.core.property;

import static org.snapscript.core.ModifierType.CONSTANT;

import org.snapscript.core.Type;
import org.snapscript.core.property.ConstantProperty;
import org.snapscript.core.property.Property;
import org.snapscript.core.property.ScopeProperty;

public class ConstantPropertyBuilder {
   
   public Property createConstant(String name) throws Exception {
      return new ScopeProperty(name, null, CONSTANT.mask);
   }

   public Property createConstant(String name, Object value) {
      return new ConstantProperty(name, null, null, value, CONSTANT.mask);
   }
   
   public Property createConstant(String name, Object value, Type type) {
      return new ConstantProperty(name, type, null, value, CONSTANT.mask);
   }
}