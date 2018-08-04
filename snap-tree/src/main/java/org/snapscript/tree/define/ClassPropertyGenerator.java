package org.snapscript.tree.define;

import static org.snapscript.core.ModifierType.CONSTANT;
import static org.snapscript.core.Reserved.TYPE_CLASS;
import static org.snapscript.core.Reserved.TYPE_THIS;
import static org.snapscript.core.variable.Constant.TYPE;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeBody;
import org.snapscript.core.variable.Value;

public class ClassPropertyGenerator {
   
   private final ConstantPropertyBuilder builder;
   
   public ClassPropertyGenerator() {
      this.builder = new ConstantPropertyBuilder();
   }

   public void generate(TypeBody body, Scope scope, Type type) throws Exception {
      Constraint constraint = Constraint.getConstraint(type, CONSTANT.mask);
      Value value = Value.getConstant(type, TYPE);
      Scope outer = type.getScope();
      State state = outer.getState();
      
      builder.createInstanceProperty(TYPE_THIS, type, constraint);
      builder.createStaticProperty(body, TYPE_CLASS, type, TYPE);
      state.add(TYPE_CLASS, value);
   }
}