package org.snapscript.core.property;

import static org.snapscript.core.ModifierType.CONSTANT;

import org.snapscript.core.Type;
import org.snapscript.core.constraint.IdentityConstraint;
import org.snapscript.core.constraint.Constraint;

public class ConstantPropertyBuilder {
   
   private final Constraint none;
   
   public ConstantPropertyBuilder() {
      this.none = new IdentityConstraint(null);
   }
   
//   public Property createConstant(String name) throws Exception {
//      return new ScopeProperty(name, null, none, CONSTANT.mask);
//   }
//
//   public Property createConstant(String name, Object value) {
//      return new ConstantProperty(name, null, none, value, CONSTANT.mask);
//   }
//   
//   public Property createConstant(String name, Object value, Type type) {
//      return new ConstantProperty(name, type, none, value, CONSTANT.mask);
//   }
   
   public Property createConstant(String name, Object value, Type type, Constraint constraint) {
      return new ConstantProperty(name, type, constraint, value, CONSTANT.mask);
   }
}