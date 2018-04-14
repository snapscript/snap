package org.snapscript.tree.define;

import static org.snapscript.core.ModifierType.CONSTANT;
import static org.snapscript.core.Reserved.ENUM_NAME;
import static org.snapscript.core.Reserved.ENUM_ORDINAL;
import static org.snapscript.core.Reserved.ENUM_VALUES;
import static org.snapscript.core.Reserved.TYPE_CLASS;
import static org.snapscript.core.Reserved.TYPE_THIS;
import static org.snapscript.core.constraint.Constraint.INTEGER;
import static org.snapscript.core.constraint.Constraint.LIST;
import static org.snapscript.core.constraint.Constraint.STRING;
import static org.snapscript.core.constraint.Constraint.TYPE;

import java.util.List;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeBody;
import org.snapscript.core.variable.Value;

public class EnumPropertyGenerator {

   protected final ConstantPropertyBuilder builder;
   
   public EnumPropertyGenerator() {
      this.builder = new ConstantPropertyBuilder();
   }
   
   public void generate(TypeBody body, Scope scope, Type type, List values) throws Exception {
      Constraint constraint = Constraint.getConstraint(type, CONSTANT.mask);
      Value value = Value.getConstant(type);
      Value list = Value.getConstant(values);
      Scope outer = type.getScope();
      State state = outer.getState();

      builder.createStaticProperty(body, TYPE_CLASS, type, TYPE);
      builder.createStaticProperty(body, ENUM_VALUES, type, LIST);
      builder.createInstanceProperty(TYPE_THIS, type, constraint);      
      builder.createInstanceProperty(ENUM_NAME, type, STRING); // might declare name as property many times
      builder.createInstanceProperty(ENUM_ORDINAL, type, INTEGER);
      state.add(TYPE_CLASS, value);
      state.add(ENUM_VALUES, list);
   }
}