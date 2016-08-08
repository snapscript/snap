package org.snapscript.tree.define;

import static org.snapscript.core.ModifierType.CONSTANT;

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
}
